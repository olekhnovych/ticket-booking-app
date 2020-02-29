package com.github.olekhnovych.ticketbooking.dto

import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormatter, ISODateTimeFormat}

import akka.http.scaladsl.unmarshalling.Unmarshaller
import spray.json.DefaultJsonProtocol


trait DateTimeUnmarshaller {
  private val formatter : DateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis()

  implicit val dateTimeFromStringUnmarshaller: Unmarshaller[String, DateTime] =
    Unmarshaller.strict[String, DateTime](formatter.parseDateTime)
}
