package com.alexdupre.shapeshift.models

import play.api.libs.json._

object Availability extends Enumeration {
  type Availability = Value

  val Available   = Value("available")
  val Unavailable = Value("unavailable")

  implicit val format = Format(Enumeration.reads(Availability), Enumeration.writes)
}

object OrderStatus extends Enumeration {
  type OrderStatus = Value

  val NoDeposits     = Value("no_deposits")
  val Expired        = Value("expired")
  val Received       = Value("received")
  val Complete       = Value("complete")
  val ContactSupport = Value("contact_support")

  implicit val format = Format(Enumeration.reads(OrderStatus), Enumeration.writes)
}

object Enumeration {

  def reads[E <: Enumeration](enum: E): Reads[E#Value] =
    new Reads[E#Value] {
      def reads(json: JsValue) = json match {
        case JsString(s) => {
          try {
            JsSuccess(enum.withName(s))
          } catch {
            case _: NoSuchElementException =>
              JsError(s"Enumeration expected of type: '${enum.getClass}', but it does not appear to contain the value: '$s'")
          }
        }
        case JsBoolean(b) => {
          try {
            JsSuccess(enum.withName(b.toString()))
          } catch {
            case _: NoSuchElementException =>
              JsError(s"Enumeration expected of type: '${enum.getClass}', but it does not appear to contain the value: '$b'")
          }
        }
        case _ => JsError("String value expected")
      }
    }

  def writes[E <: Enumeration]: Writes[E#Value] =
    new Writes[E#Value] {
      def writes(v: E#Value): JsValue = JsString(v.toString)
    }

}
