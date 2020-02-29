package com.github.olekhnovych.ticketbooking.dto

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


case class CreateScreeningRoom(name: String,
                               rowsNumber: Int,
                               seatsNumber: Int)


trait CreateScreeningRoomJsonSupport extends SprayJsonSupport
    with DefaultJsonProtocol {

  implicit val createScreeningRoomeFormat = jsonFormat3(CreateScreeningRoom)
}
