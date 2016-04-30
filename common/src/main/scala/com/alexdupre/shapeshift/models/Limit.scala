package com.alexdupre.shapeshift.models

import play.api.libs.json.Json

case class Limit(pair: Market, limit: BigDecimal, min: BigDecimal)

object Limit {

  implicit val format = Json.format[Limit]

}
