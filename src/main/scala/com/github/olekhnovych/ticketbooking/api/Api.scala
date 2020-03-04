package com.github.olekhnovych.ticketbooking.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}

import com.typesafe.scalalogging.LazyLogging

import com.github.olekhnovych.ticketbooking.{dto => dto}


trait ApiComponent
    extends LazyLogging
    with dto.ErrorMessageJsonSupport{

  this: MoviesApiComponent
      with ScreeningTimesApiComponent
      with ScreeningRoomsApiComponent
      with ReservationsApiComponent =>

 def exceptionHandler: ExceptionHandler =
   ExceptionHandler {
     case dto.ServiceError(httpStatus, errorMessage) =>
       complete(httpStatus, errorMessage)
     case exception: Exception => {
       logger.error("InternalServerError", exception)
       complete(StatusCodes.InternalServerError, exception.getMessage())
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
