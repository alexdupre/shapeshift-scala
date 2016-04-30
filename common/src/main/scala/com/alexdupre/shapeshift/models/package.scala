package com.alexdupre.shapeshift

import java.time.Instant

import play.api.data.validation.ValidationError
import play.api.libs.json._

package object models {

  implicit val instantFormat = new Format[Instant] {
    override def reads(json: JsValue): JsResult[Instant] = json match {
      case JsNumber(d) => JsSuccess(Instant.ofEpochMilli(d.toLong))
      case _ => JsError(Seq(JsPath() -> Seq(ValidationError("error.expected.instant"))))
    }
    override def writes(o: Instant): JsValue = JsNumber(o.toEpochMilli)
  }

  implicit val customBigDecimalFormat = new Format[BigDecimal] {
    override def reads(json: JsValue): JsResult[BigDecimal] = json match {
      case JsString("NaN") => JsSuccess(BigDecimal(0))
      case JsString(s) =>
        scala.util.control.Exception.catching(classOf[NumberFormatException])
          .opt(JsSuccess(BigDecimal(new java.math.BigDecimal(s))))
          .getOrElse(JsError(ValidationError("error.expected.numberformatexception")))
      case JsNumber(d) => JsSuccess(d.underlying)
      case JsNull => JsSuccess(BigDecimal(0))
      case _ => JsError(ValidationError("error.expected.jsnumberorjsstring"))
    }
    override def writes(o: BigDecimal): JsValue = JsString(o.underlying().toPlainString)
  }

}
