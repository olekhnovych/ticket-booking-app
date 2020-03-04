package com.github.olekhnovych.ticketbooking.api

import org.joda.time.DateTime

import com.github.olekhnovych.ticketbooking.service._
import com.github.olekhnovych.ticketbooking.{dto => dto}

import akka.http.scaladsl.server.Directives._


trait ScreeningTimesApiComponent extends
    dto.JustIdJsonSupport
    with dto.CreateScreeningTimeJsonSupport
    with dto.DateTimeUnmarshaller
    with dto.ScreeningTimeJsonSupport {

  this: ScreeningTimesServiceComponent =>

  def screeningTimesApiRoute =
    path("screening-times") {
      concat(
        post {
          entity(as[dto.CreateScreeningTime]) { screeningTime =>
            complete(screeningTimesService.createScreeningTime(screeningTime))
          }
        },
        get {
          parameters('from.as[DateTime], 'to.as[DateTime]) { (from, to) =>
            complete(screeningTimesService.findScreeningTimesByTimeInterval(from, to))
          }
        }
      )
    }
}
