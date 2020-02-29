package com.github.olekhnovych.ticketbooking.dto

import org.joda.time.Period
import org.joda.time.format.{PeriodFormatter, ISOPeriodFormat}

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, DeserializationException, JsString, JsValue, JsonFormat}


trait PeriodJsonSupport extends SprayJsonSupport
{
  implicit val periodJsonFormat: JsonFormat[Period] = new JsonFormat[Period] {
    private val formatter: PeriodFormatter = ISOPeriodFormat.standard()

    override def write(period: Period) = JsString(formatter.print(period))

    override def read(jsValue: JsValue) : Period = jsValue match {
      case JsString(s) => formatter.parsePeriod(s)
      case _ => throw DeserializationException("Invalid period format: " + jsValue)
    }
  }
}
