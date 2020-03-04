package com.github.olekhnovych.ticketbooking

import com.github.olekhnovych.ticketbooking.api._
import com.github.olekhnovych.ticketbooking.service._

import slick.jdbc.PostgresProfile.api._

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer


trait Components extends
    DatabaseComponent
    with DateTimeFactoryComponent
    with MoviesServiceComponent
    with ScreeningRoomsServiceComponent
    with ScreeningTimesServiceComponent
    with ReservationsServiceComponent
    with TicketPricesServiceComponent
    with MoviesApiComponent
    with ScreeningTimesApiComponent
    with ScreeningRoomsApiComponent
    with ReservationsApiComponent
    with ApiComponent
    with ActorSystemComponent
    with ExecutionContextComponent
    with HttpComponent
    with BackgroundComponent


object Registry extends Components {
  val database = Database.forConfig("main")
  val moviesService = new MoviesService()
  val screeningTimesService = new ScreeningTimesService()
  val screeningRoomsService = new ScreeningRoomsService()
  val reservationsService = new ReservationsService()
  val ticketPricesService = new TicketPricesService()
  val dateTimeFactory = realtimeDateTimeFactory
  implicit val actorSystem = ActorSystem("system")
  implicit val actorMaterializer = ActorMaterializer()
  implicit val executionContext = actorMaterializer.executionContext
}


object Boot extends App {
  Registry.startBackgroud()
  Registry.httpBindAndHandle()
}
