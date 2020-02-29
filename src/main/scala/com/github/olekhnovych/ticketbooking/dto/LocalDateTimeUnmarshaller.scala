package com.github.olekhnovych.ticketbooking.dto

import org.joda.time.LocalDateTime
import org.joda.time.format.{DateTimeFormatter, ISODateTimeFormat}

import akka.http.scaladsl.unmarshalling.Unmarshaller
import spray.json.DefaultJsonProtocol


trait LocalDateTimeUnmarshaller {
  private val formatter : DateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis()

  implicit val dateTimeFromStringUnmarshaller: Unmarshaller[String, LocalDateTime] =
    Unmarshaller.strict[String, LocalDateTime](formatter.parseLocalDateTime)
}
