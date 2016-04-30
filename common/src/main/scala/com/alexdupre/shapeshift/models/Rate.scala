package com.alexdupre.shapeshift.models

import play.api.libs.json.Json

case class Rate(pair: Market, rate: BigDecimal)

object Rate {

  implicit val format = Json.format[Rate]

}
