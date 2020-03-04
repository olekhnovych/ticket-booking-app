package com.github.olekhnovych.ticketbooking.dto

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


case class Seat(id: Id,
                row: Int,
                seat: Int,
                reserved: Boolean)


trait SeatJsonSupport extends SprayJsonSupport
    with DefaultJsonProtocol {

  implicit val seatFormat = jsonFormat4(Seat)
}
