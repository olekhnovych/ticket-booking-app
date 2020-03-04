package com.github.olekhnovych.ticketbooking.dto

import org.joda.time.{DateTime, Period}

import monocle.Lens
import monocle.macros.GenLens

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


case class ScreeningTime(id: Id,
                         start: DateTime,
                         duration: Period,
                         movie: Movie)


object ScreeningTimeLens {
  lazy val id: Lens[ScreeningTime, Id] = GenLens[ScreeningTime](_.id)
}


trait ScreeningTimeJsonSupport extends SprayJsonSupport
    with DefaultJsonProtocol
    with MovieJsonSupport
    with DateTimeJsonSupport
    with PeriodJsonSupport {

  implicit val screeningTimeFormat = jsonFormat4(ScreeningTime)
}
