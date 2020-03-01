package com.github.olekhnovych.ticketbooking.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}

import com.typesafe.scalalogging.LazyLogging


trait ApiComponent extends LazyLogging {
  this: MoviesApiComponent
      with ScreeningTimesApiComponent
      with ScreeningRoomsApiComponent
      with ReservationsApiComponent =>

 def exceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case exception: Exception => {
        logger.error("", exception)
        complete(HttpResponse(StatusCodes.Conflict, entity=exception.getMessage()))
      }
    }

  def apiRoute =
    handleExceptions(exceptionHandler) {
      //TODO auth?
      concat(
        moviesApiRoute,
        screeningTimesApiRoute,
        screeningRoomsApiRoute,
        reservationsApiRoute,
        )
    }
}
