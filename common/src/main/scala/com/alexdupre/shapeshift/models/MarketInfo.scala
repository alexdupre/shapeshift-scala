package com.alexdupre.shapeshift.models

import play.api.libs.json.Json
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class MarketInfo(
    pair: Market,
    rate: BigDecimal,
    limit: BigDecimal,
    maxLimit: BigDecimal,
    min: BigDecimal,
    minerFee: Option[BigDecimal]
)

object MarketInfo {

  implicit val format = Format[MarketInfo](
    (
      (__ \ "pair").read[Market] and
        (__ \ "rate").read[BigDecimal](customBigDecimalFormat) and
        (__ \ "limit").read[BigDecimal] and
        (__ \ "maxLimit").read[BigDecimal] and
        ((__ \ "min").read[BigDecimal] orElse (__ \ "minimum").read[BigDecimal]) and
        (__ \ "minerFee").readNullable[BigDecimal]
    )(MarketInfo.apply _),
    Json.writes[MarketInfo]
  )

}
