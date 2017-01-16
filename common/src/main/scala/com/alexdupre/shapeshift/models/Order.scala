package com.alexdupre.shapeshift.models

import java.time.Instant

import play.api.libs.json.Json

case class Order(orderId: String,
                 pair: Market,
                 sAddress: Option[String],
                 deposit: String,
                 depositAmount: BigDecimal,
                 withdrawal: String,
                 withdrawalAmount: BigDecimal,
                 returnAddress: Option[String],
                 expiration: Instant,
                 quotedRate: BigDecimal,
                 maxLimit: BigDecimal,
                 minerFee: BigDecimal,
                 xrpDestTag: Option[String],
                 public: Option[String])

object Order {

  implicit val format = Json.format[Order]

}
