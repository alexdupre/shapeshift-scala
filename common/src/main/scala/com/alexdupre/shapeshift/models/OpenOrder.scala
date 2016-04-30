package com.alexdupre.shapeshift.models

import play.api.libs.json.Json

case class OpenOrder(orderId: String, sAddress: Option[String],
                     deposit: String, depositType: Coin,
                     withdrawal: String, withdrawalType: Coin,
                     returnAddress: String, returnAddressType: Coin)

object OpenOrder {

  implicit val format = Json.format[OpenOrder]

}
