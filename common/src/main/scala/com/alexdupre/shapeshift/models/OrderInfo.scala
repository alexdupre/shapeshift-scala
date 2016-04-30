package com.alexdupre.shapeshift.models

import com.alexdupre.shapeshift.models.OrderStatus.OrderStatus
import play.api.libs.json.{Format, JsValue, Json, Reads}

sealed trait OrderInfo {
  def status: OrderStatus
}

sealed trait OrderNoDeposit {
  def incomingCoin: Coin
  def deposit: String
  def incomingCoinInfo: CoinInfo
  def outgoingCoin: Coin
  def withdrawal: String
  def outgoingCoinInfo: CoinInfo
}

sealed trait OrderWithDeposit {
  def orderId: String
  def incomingType: Coin
  def deposit: String
  def incomingCoin: BigDecimal
  def incomingCoinInfo: CoinInfo
}

case class OpenOrderNoDeposit(status: OrderStatus,
                              incomingCoin: Coin, deposit: String, incomingCoinInfo: CoinInfo,
                              outgoingCoin: Coin, withdrawal: String, outgoingCoinInfo: CoinInfo
                             ) extends OrderInfo with OrderNoDeposit

case class FixedOrderNoDeposit(status: OrderStatus,
                               incomingCoin: Coin, deposit: String, incomingCoinInfo: CoinInfo, depositAmount: BigDecimal,
                               outgoingCoin: Coin, withdrawal: String, outgoingCoinInfo: CoinInfo, withdrawalAmount: BigDecimal,
                               timeRemaining: BigDecimal, rate: BigDecimal
                              ) extends OrderInfo with OrderNoDeposit

case class OrderExpired(status: OrderStatus,
                        incomingCoin: Coin, deposit: String, incomingCoinInfo: CoinInfo, depositAmount: BigDecimal,
                        outgoingCoin: Coin, withdrawal: String, outgoingCoinInfo: CoinInfo, withdrawalAmount: BigDecimal,
                        timeRemaining: BigDecimal, rate: BigDecimal
                       ) extends OrderInfo with OrderNoDeposit

case class OrderReceived(orderId: String, status: OrderStatus,
                         incomingType: Coin, deposit: String, incomingCoinInfo: CoinInfo, incomingCoin: BigDecimal
                        ) extends OrderInfo with OrderWithDeposit

case class OrderComplete(orderId: String, status: OrderStatus,
                         incomingType: Coin, deposit: String, incomingCoinInfo: CoinInfo, incomingCoin: BigDecimal,
                         outgoingType: Coin, withdraw: String, outgoingCoinInfo: CoinInfo, outgoingCoin: BigDecimal,
                         rate: BigDecimal, transaction: String, transactionURL: String
                        ) extends OrderInfo with OrderWithDeposit

object OrderInfo {

  implicit val openOrderNoDepositFormat = Json.format[OpenOrderNoDeposit]
  implicit val fixedOrderNoDepositFormat = Json.format[FixedOrderNoDeposit]
  implicit val orderExpiredFormat = Json.format[OrderExpired]
  implicit val orderReceivedFormat = Json.format[OrderReceived]
  implicit val orderCompleteFormat = Json.format[OrderComplete]

  implicit val format = new Format[OrderInfo] {

    def reads(json: JsValue) = ((json \ "status").as[OrderStatus], (json \ "type").as[Int]) match {
      case (OrderStatus.NoDeposits, 1) => json.validate[OpenOrderNoDeposit]
      case (OrderStatus.NoDeposits, 2) => json.validate[FixedOrderNoDeposit]
      case (OrderStatus.Expired, _) => json.validate[OrderExpired]
      case (OrderStatus.Received, _) => json.validate[OrderReceived]
      case (OrderStatus.Complete, _) => json.validate[OrderComplete]
    }

    def writes(oi: OrderInfo): JsValue = oi match {
      case o: OpenOrderNoDeposit => Json.toJson(o)
      case o: FixedOrderNoDeposit => Json.toJson(o)
      case o: OrderExpired => Json.toJson(o)
      case o: OrderReceived => Json.toJson(o)
      case o: OrderComplete => Json.toJson(o)
    }
  }

}
