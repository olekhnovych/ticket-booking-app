package com.github.olekhnovych.ticketbooking.dto

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


case class CreateReservation(person: Person,
                             screeningTimeId: Id,
                             reservationSeats: List[ReservationSeat]) {

  def seatIds = reservationSeats.map(_.seatId)
  def ticketTypes = reservationSeats.map(_.ticketType)
}


trait CreateReservationJsonSupport extends SprayJsonSupport
    with DefaultJsonProtocol
    with ReservationSeatJsonSupport
    with PersonJsonSupport {

  implicit val createReservationFormat = jsonFormat3(CreateReservation)
}
