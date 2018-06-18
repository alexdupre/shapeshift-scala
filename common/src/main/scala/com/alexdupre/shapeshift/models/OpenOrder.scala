package com.alexdupre.shapeshift.models

import play.api.libs.json.Json

case class OpenOrder(
    orderId: String,
    sAddress: Option[String],
    deposit: String,
    depositType: Coin,
    withdrawal: String,
    withdrawalType: Coin,
    returnAddress: Option[String],
    returnAddressType: Option[Coin],
    xrpDestTag: Option[String],
    public: Option[String]
)

object OpenOrder {

  implicit val format = Json.format[OpenOrder]

}
