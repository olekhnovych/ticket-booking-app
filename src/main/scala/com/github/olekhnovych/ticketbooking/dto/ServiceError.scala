package com.github.olekhnovych.ticketbooking.dto

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

import akka.http.scaladsl.model.{StatusCodes, StatusCode}


case class ErrorMessage(code: String, message: String)
case class ServiceError(httpStatus: StatusCode, errorMessage: ErrorMessage) extends Exception


object ServiceError {
  def badRequest(code: String, message: String) = ServiceError(StatusCodes.BadRequest, ErrorMessage(code, message))
  def conflict(code: String, message: String) = ServiceError(StatusCodes.Conflict, ErrorMessage(code, message))
  def notFound(code: String, message: String) = ServiceError(StatusCodes.NotFound, ErrorMessage(code, message))
}


trait ErrorMessageJsonSupport extends SprayJsonSupport
    with DefaultJsonProtocol {

  implicit val errorMessageFormat = jsonFormat2(ErrorMessage)
}
