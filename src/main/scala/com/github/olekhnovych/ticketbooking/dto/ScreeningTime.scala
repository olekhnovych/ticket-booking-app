package com.github.olekhnovych.ticketbooking.dto

import org.joda.time.{DateTime, Period}

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


case class ScreeningTime(id: Id,
                         start: DateTime,
                         duration: Period,
                         movie: Movie)


trait ScreeningTimeJsonSupport extends SprayJsonSupport
    with DefaultJsonProtocol
    with MovieJsonSupport
    with DateTimeJsonSupport
    with PeriodJsonSupport {

  implicit val screeningTimeFormat = jsonFormat4(ScreeningTime)
}
