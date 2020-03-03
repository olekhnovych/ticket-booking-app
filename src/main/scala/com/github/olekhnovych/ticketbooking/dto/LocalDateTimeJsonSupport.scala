package com.github.olekhnovych.ticketbooking.dto

import org.joda.time.LocalDateTime
import org.joda.time.format.{DateTimeFormatter, ISODateTimeFormat}

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, DeserializationException, JsString, JsValue, JsonFormat}


trait LocalDateTimeJsonSupport extends SprayJsonSupport
{
  implicit val localDateTimeJsonFormat: JsonFormat[LocalDateTime] = new JsonFormat[LocalDateTime] {
    private val formatter : DateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis()

    override def write(localDateTime: LocalDateTime) = JsString(formatter.print(localDateTime))

    override def read(jsValue: JsValue) : LocalDateTime = jsValue match {
      case JsString(s) => formatter.parseLocalDateTime(s)
      case _ => throw ServiceError.badRequest("INVALID_DATETIME", "Invalid date format: " + jsValue)
    }
  }
}
