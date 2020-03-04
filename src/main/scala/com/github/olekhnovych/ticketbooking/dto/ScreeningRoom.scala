package com.github.olekhnovych.ticketbooking.dto

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


case class ScreeningRoom(name: String)


trait ScreeningRoomJsonSupport extends SprayJsonSupport
    with DefaultJsonProtocol {

  implicit val screeningRoomeFormat = jsonFormat1(ScreeningRoom)
}
