package com.github.olekhnovych.ticketbooking.dto

import org.joda.time.{DateTime, Period}

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


case class CreateScreeningTime(start: DateTime,
                               duration: Period,
                               movieId: Id,
                               screeningRoomId: Id)


trait CreateScreeningTimeJsonSupport extends SprayJsonSupport
    with DefaultJsonProtocol
    with DateTimeJsonSupport
    with PeriodJsonSupport {

  implicit val createScreeningTimeFormat = jsonFormat4(CreateScreeningTime)
}
