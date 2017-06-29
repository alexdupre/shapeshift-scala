package com.alexdupre.shapeshift

import com.alexdupre.shapeshift.models._
import com.alexdupre.shapeshift.providers.{ProviderAPI, ProviderResponse}
import org.slf4j.LoggerFactory
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}
import scala.language.{postfixOps, reflectiveCalls}

class ShapeShiftClient(provider: ProviderAPI, pubApiKey: String = ShapeShiftClient.pubApiKey)(implicit ec: ExecutionContext)
    extends ShapeShiftAPI {

  val logger = LoggerFactory.getLogger(classOf[ShapeShiftClient])

  val apiUrl = "https://shapeshift.io/"

  private def get[T](path: String)(implicit fjs: Reads[T]): Future[T] = {
    val endpoint = apiUrl + path
    logger.debug(s"Request: GET ${endpoint}")
    provider.get(endpoint).map(handleResponse[T](_))
  }

  private def post[T](path: String, params: JsValue, encapsulated: Boolean = false)(implicit fjs: Reads[T]): Future[T] = {
    val endpoint = apiUrl + path
    logger.debug(s"Request: POST ${endpoint}")
    logger.trace(s"Payload:\n${Json.prettyPrint(params)}")
    provider.post(endpoint, params).map(handleResponse[T](_, encapsulated))
  }

  private def handleResponse[T](response: ProviderResponse, encapsulated: Boolean = false)(implicit fjs: Reads[T]): T = {
    val js = response.body
    logger.debug(s"Response Status: ${response.status}")
    logger.trace(s"Response:\n${Json.prettyPrint(js)}")
    if (response.status == 200) {
      val jsres = js match {
        case o: JsObject =>
          o.value.get("error") match {
            case Some(JsString(error)) => throw new ShapeShiftException(error)
            case _ =>
              (if (encapsulated) o.value.get("success") else Some(js)) match {
                case Some(value) => value.validate[T]
                case _           => throw new RuntimeException("ShapeShift Protocol Exception")
              }
          }
        case a: JsArray => a.validate[T]
        case _          => throw new RuntimeException("ShapeShift Protocol Exception")
      }
      jsres match {
        case JsSuccess(r, _) => r
        case JsError(e)      => throw new RuntimeException("ShapeShift Parser Exception", new JsResultException(e))

      }
    } else {
      throw new RuntimeException("ShapeShift Protocol Exception")
    }
  }

  override def getRate(market: Market): Future[Rate] = {
    get[Rate](s"rate/$market")
  }

  override def getDepositLimit(market: Market): Future[Limit] = {
    get[Limit](s"limit/$market")
  }

  override def getMarketInfo(market: Market): Future[MarketInfo] = {
    get[MarketInfo](s"marketinfo/$market")
  }

  override def getMarketsInfo(): Future[Seq[MarketInfo]] = {
    get[Seq[MarketInfo]]("marketinfo")
  }

  override def getCoins(): Future[Map[Coin, CoinInfo]] = {
    get[Map[String, CoinInfo]]("getcoins").map(_.map {
      case (coin, info) => Coin.from(coin) -> info
    })
  }

  override def validateAddress(coin: Coin, address: String): Future[Unit] = {
    get[JsObject](s"validateAddress/$address/$coin").map(_ => ())
  }

  override def createOpenTransaction(market: Market,
                                     outputAddress: String,
                                     outputSpecial: Option[(String, String)] = None,
                                     returnAddress: Option[String] = None): Future[OpenOrder] = {
    val req = Json.obj(
      "pair"          -> market,
      "withdrawal"    -> outputAddress,
      "returnAddress" -> returnAddress,
      "apiKey"        -> pubApiKey
    ) ++ outputSpecial.fold(Json.obj()) {
      case (key, value) => Json.obj(key -> value)
    }
    post[OpenOrder]("shift", req)
  }

  override def createFixedInputTransaction(market: Market,
                                           inputAmount: BigDecimal,
                                           outputAddress: String,
                                           outputSpecial: Option[(String, String)] = None,
                                           returnAddress: Option[String] = None): Future[Order] = {
    val req = Json.obj(
      "pair"          -> market,
      "depositAmount" -> inputAmount,
      "withdrawal"    -> outputAddress,
      "returnAddress" -> returnAddress,
      "apiKey"        -> pubApiKey
    ) ++ outputSpecial.fold(Json.obj()) {
      case (key, value) => Json.obj(key -> value)
    }
    post[Order]("sendamount", req, true)
  }

  override def createFixedOutputTransaction(market: Market,
                                            outputAmount: BigDecimal,
                                            outputAddress: String,
                                            outputSpecial: Option[(String, String)] = None,
                                            returnAddress: Option[String] = None): Future[Order] = {
    val req = Json.obj(
      "pair"          -> market,
      "amount"        -> outputAmount,
      "withdrawal"    -> outputAddress,
      "returnAddress" -> returnAddress,
      "apiKey"        -> pubApiKey
    ) ++ outputSpecial.fold(Json.obj()) {
      case (key, value) => Json.obj(key -> value)
    }
    post[Order]("sendamount", req, true)
  }

  override def quoteFixedInputTransaction(market: Market, inputAmount: BigDecimal): Future[Quote] = {
    val req = Json.obj(
      "pair"          -> market,
      "depositAmount" -> inputAmount
    )
    post[Quote]("sendamount", req, true)
  }

  override def quoteFixedOutputTransaction(market: Market, outputAmount: BigDecimal): Future[Quote] = {
    val req = Json.obj(
      "pair"   -> market,
      "amount" -> outputAmount
    )
    post[Quote]("sendamount", req, true)
  }

  override def getOrderInfo(orderId: String): Future[OrderInfo] = {
    get[OrderInfo](s"orderInfo/$orderId")
  }

}

object ShapeShiftClient {

  val pubApiKey =
    "5e2d0e481642e21d2252704ebd4a370efbe50037475544ea599bee265658b555010adc69ef4415c4c3d8fe3be62587b05157bd559adcd8609fd54a731a87094a"

}
