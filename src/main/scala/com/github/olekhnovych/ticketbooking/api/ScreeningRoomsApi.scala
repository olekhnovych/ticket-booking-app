package com.github.olekhnovych.ticketbooking.api

import com.github.olekhnovych.ticketbooking.service._
import com.github.olekhnovych.ticketbooking.{dto => dto}

import akka.http.scaladsl.server.Directives._


trait ScreeningRoomsApiComponent extends
    dto.JustIdJsonSupport
    with dto.CreateScreeningRoomJsonSupport {

  this: ScreeningRoomsServiceComponent =>

  def screeningRoomsApiRoute =
    path("screening-rooms") {
      post {
        entity(as[dto.CreateScreeningRoom]) { screeningRoom =>
          complete(screeningRoomsService.createScreeningRoom(screeningRoom))
        }
      }
    }
}
