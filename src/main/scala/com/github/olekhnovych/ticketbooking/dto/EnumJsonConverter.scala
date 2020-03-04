package com.github.olekhnovych.ticketbooking.dto

import scala.util.{Try, Success, Failure}

import spray.json.{DeserializationException, JsString, JsValue, RootJsonFormat}


// https://github.com/spray/spray-json/issues/200
class EnumJsonConverter[T <: scala.Enumeration](enu: T) extends RootJsonFormat[T#Value] {
  override def write(obj: T#Value): JsValue = JsString(obj.toString)

  override def read(json: JsValue): T#Value = {
    json match {
      case JsString(txt) => {
        Try {
          enu.withName(txt)
        } match {
          case Success(value) => value
          case _ => throw ServiceError.badRequest("INVALID_VALUE", s"Expected a value from enum $enu instead of $txt")
        }
      }
      case somethingElse => throw ServiceError.badRequest("INVALID_VALUE", s"Expected a value from enum $enu instead of $somethingElse")
    }
  }
}
