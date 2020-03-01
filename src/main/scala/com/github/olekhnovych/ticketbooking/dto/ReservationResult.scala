package com.github.olekhnovych.ticketbooking.dto

import org.joda.time.DateTime
import akka.http.scaladsl.model.Uri

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


case class ReservationResult(totalAmountToPay: Double,
                             expirationTime: DateTime,
                             confirmUrl: ConfirmReservationResource)


trait ReservationResultJsonSupport extends SprayJsonSupport
    with DefaultJsonProtocol
    with DateTimeJsonSupport
    with ConfirmReservationResourceJsonSupport {

  implicit val reservationResultFormat = jsonFormat3(ReservationResult)
}
