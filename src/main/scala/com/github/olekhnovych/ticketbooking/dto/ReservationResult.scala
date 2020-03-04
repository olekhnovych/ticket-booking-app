package com.github.olekhnovych.ticketbooking.dto

import org.joda.time.DateTime
import akka.http.scaladsl.model.Uri

import monocle.Lens
import monocle.macros.GenLens

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


case class ReservationResult(totalAmountToPay: Double,
                             expirationTime: DateTime,
                             confirmUrl: ConfirmReservationResource)


object ReservationResultLens {
  lazy val confirmUrl: Lens[ReservationResult, ConfirmReservationResource] = GenLens[ReservationResult](_.confirmUrl)
}


trait ReservationResultJsonSupport extends SprayJsonSupport
    with DefaultJsonProtocol
    with DateTimeJsonSupport
    with ConfirmReservationResourceJsonSupport {

  implicit val reservationResultFormat = jsonFormat3(ReservationResult)
}
