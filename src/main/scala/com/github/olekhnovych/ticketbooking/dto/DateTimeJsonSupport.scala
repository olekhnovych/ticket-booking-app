package com.github.olekhnovych.ticketbooking.dto

import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormatter, ISODateTimeFormat}

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, DeserializationException, JsString, JsValue, JsonFormat}


trait DateTimeJsonSupport extends SprayJsonSupport
{
  implicit val dateTimeJsonFormat: JsonFormat[DateTime] = new JsonFormat[DateTime] {
    private val formatter : DateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis()

    override def write(dateTime: DateTime) = JsString(formatter.print(dateTime))

    override def read(jsValue: JsValue) : DateTime = jsValue match {
      case JsString(s) => formatter.parseDateTime(s)
      case _ => throw DeserializationException("Invalid date format: " + jsValue)
    }
  }
}
