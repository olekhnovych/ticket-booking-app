package com.github.olekhnovych.ticketbooking.api

import com.github.olekhnovych.ticketbooking.service._
import com.github.olekhnovych.ticketbooking.{dto => dto}

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.{StatusCodes, HttpEntity}


trait ReservationsApiComponent extends
    dto.JustIdJsonSupport
    with dto.CreateReservationJsonSupport
    with dto.SeatJsonSupport {

  this: ReservationsServiceComponent =>

  def reservationsApiRoute =
    pathPrefix("reservations") {
      extractUri { reservationsBaseUri =>
        {
          //TODO is there a better way to do this?
          trait BaseUriReservationResultJsonSupport extends dto.ReservationResultJsonSupport
              with dto.ConfirmReservationResourceJsonSupport {

            val baseUri = reservationsBaseUri
          }

          object BaseUriReservationResultJsonSupport extends BaseUriReservationResultJsonSupport
          import BaseUriReservationResultJsonSupport.reservationResultFormat

          concat(
            path("seats") {
              get {
                parameters('screeningTimeId.as[dto.Id]) { screeningTimeId =>
                  complete(reservationsService.getSeatsByScreeningTime(screeningTimeId))
                }
              }
            },
            path(IntNumber / "confirm") { reservationId =>
              {
                post {
                  onSuccess(reservationsService.confirmReservation(reservationId)) {
                    complete(StatusCodes.NoContent, HttpEntity.Empty)
                  }
                }
              }
            },
            post {
              entity(as[dto.CreateReservation]) { createReservation =>
                complete(reservationsService.createReservation(createReservation))
              }
            }
          )
        }
      }
    }
}
