package com.alexdupre.shapeshift.models

import java.time.Instant

import play.api.libs.json.Json

case class Quote(orderId: String,
                 pair: Market,
                 withdrawalAmount: BigDecimal,
                 depositAmount: BigDecimal,
                 expiration: Instant,
                 quotedRate: BigDecimal,
                 maxLimit: BigDecimal,
                 minerFee: BigDecimal)

object Quote {

  implicit val format = Json.format[Quote]

}
