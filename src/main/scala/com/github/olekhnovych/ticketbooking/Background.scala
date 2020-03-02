package com.github.olekhnovych.ticketbooking.service

import scala.concurrent.duration._


trait BackgroundComponent {
  this: ReservationsServiceComponent
      with ActorSystemComponent
      with ExecutionContextComponent =>

  def startBackgroud() = {
    actorSystem.scheduler.schedule(Duration.Zero, 1.minute) {
      reservationsService.deleteExpiredReservations()
    }
  }
}
