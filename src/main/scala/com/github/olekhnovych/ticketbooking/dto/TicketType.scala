package com.github.olekhnovych.ticketbooking.dto

import scala.Enumeration

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport


object TicketType extends Enumeration
{
  val adult = Value("adult")
  val student = Value("student")
  val child = Value("child")
}


trait TicketTypeJsonSupport extends SprayJsonSupport {

  implicit val TicketTypeValueFormat = new EnumJsonConverter(TicketType)
}
