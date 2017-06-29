package com.alexdupre.shapeshift.models

import play.api.libs.json._

case class Market(input: Coin, output: Coin) {
  override def toString = s"${input}_${output}"
}

object Market {

  implicit val format = new Format[Market] {
    override def reads(json: JsValue): JsResult[Market] = json match {
      case JsString(s) =>
        s.split("_") match {
          case Array(i, o) => JsSuccess(Market(i, o))
          case _           => JsError("error.expected.market")
        }
      case _ => JsError("error.expected.market")
    }
    override def writes(o: Market): JsValue = JsString(o.toString)
  }

}
