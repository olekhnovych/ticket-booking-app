package com.github.olekhnovych.ticketbooking.dto

import akka.http.scaladsl.model.Uri

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{JsString, JsValue, JsonFormat}


case class ConfirmReservationResource(reservationId: Id)


trait ConfirmReservationResourceJsonSupport extends SprayJsonSupport
{
  val baseUri: Uri

  implicit val confirmReservationResourceJsonFormat: JsonFormat[ConfirmReservationResource] = new JsonFormat[ConfirmReservationResource] {
    override def write(confirmReservationResource: ConfirmReservationResource) =
      JsString(s"$baseUri/${confirmReservationResource.reservationId}/confirm")

    override def read(jsValue: JsValue): ConfirmReservationResource =
      throw new NotImplementedError()
  }
}
