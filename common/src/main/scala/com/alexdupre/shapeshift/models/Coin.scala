package com.alexdupre.shapeshift.models

import play.api.data.validation.ValidationError
import play.api.libs.json._

case class Coin private (symbol: String) {
  override def toString() = symbol
}

object Coin {

  implicit def from(symbol: String): Coin = new Coin(symbol.toUpperCase())

  implicit val format = new Format[Coin] {
    override def reads(json: JsValue): JsResult[Coin] = json match {
      case JsString(s) => JsSuccess(Coin(s))
      case _           => JsError(Seq(JsPath() -> Seq(ValidationError("error.expected.string"))))
    }
    override def writes(o: Coin): JsValue = JsString(o.symbol)
  }
}
