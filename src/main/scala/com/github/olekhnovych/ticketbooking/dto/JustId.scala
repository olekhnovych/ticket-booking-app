package com.github.olekhnovych.ticketbooking.dto

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


case class JustId(id: Id)


trait JustIdJsonSupport extends SprayJsonSupport
    with DefaultJsonProtocol {

  implicit val justIdFormat = jsonFormat1(JustId)
}
