package com.github.olekhnovych.ticketbooking.dto

import org.joda.time.DateTime

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


case class ReservationResult(totalAmount: Double,
                             expirationTime: DateTime,
                             confirmUrl: String)


trait ReservationResultJsonSupport extends SprayJsonSupport
    with DefaultJsonProtocol
    with DateTimeJsonSupport {

  implicit val reservationResultFormat = jsonFormat3(ReservationResult)
}
