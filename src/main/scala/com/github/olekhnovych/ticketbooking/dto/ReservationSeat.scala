package com.github.olekhnovych.ticketbooking.dto

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


case class ReservationSeat(ticketType: String,
                           seatId: Id)


trait ReservationSeatJsonSupport extends SprayJsonSupport
    with DefaultJsonProtocol {

  implicit val rservationSeatFormat = jsonFormat2(ReservationSeat)
}
