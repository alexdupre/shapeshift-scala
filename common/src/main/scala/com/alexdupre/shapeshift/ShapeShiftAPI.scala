package com.alexdupre.shapeshift

import com.alexdupre.shapeshift.models._
import play.api.libs.json.JsValue

import scala.concurrent.Future

trait ShapeShiftAPI {

  def getRate(market: Market): Future[Rate]

  def getDepositLimit(market: Market): Future[Limit]

  def getMarketInfo(market: Market): Future[MarketInfo]

  def getMarketsInfo(): Future[Seq[MarketInfo]]

  def getCoins(): Future[Map[Coin, CoinInfo]]

  def validateAddress(coin: Coin, address: String): Future[Unit]

  def createOpenTransaction(market: Market,
                            outputAddress: String,
                            outputSpecial: Option[(String, String)] = None,
                            returnAddress: Option[String] = None): Future[OpenOrder]

  def createFixedInputTransaction(market: Market,
                                  inputAmount: BigDecimal,
                                  outputAddress: String,
                                  outputSpecial: Option[(String, String)] = None,
                                  returnAddress: Option[String] = None): Future[Order]

  def createFixedOutputTransaction(market: Market,
                                   outputAmount: BigDecimal,
                                   outputAddress: String,
                                   outputSpecial: Option[(String, String)] = None,
                                   returnAddress: Option[String] = None): Future[Order]

  def quoteFixedInputTransaction(market: Market, inputAmount: BigDecimal): Future[Quote]

  def quoteFixedOutputTransaction(market: Market, inputAmount: BigDecimal): Future[Quote]

  def getOrderInfo(orderId: String): Future[OrderInfo]

}
