package com.github.olekhnovych.ticketbooking

import org.testcontainers.containers.output.Slf4jLogConsumer
import com.typesafe.scalalogging.LazyLogging

import com.dimafeng.testcontainers.{ForAllTestContainer, PostgreSQLContainer, ServiceLogConsumer}
import org.scalatest.FunSuite

import scala.concurrent.ExecutionContext.Implicits.{global => globalExecutionContext}

import com.github.olekhnovych.ticketbooking.service._

import slick.jdbc.PostgresProfile.api.Database
import dao.DatabaseProfile.api._


trait TestComponents
    extends DatabaseComponent
    with ExecutionContextComponent
    with DateTimeFactoryComponent
    with MoviesServiceComponent
    with ScreeningRoomsServiceComponent
    with ScreeningTimesServiceComponent
    with ReservationsServiceComponent
    with TicketPricesServiceComponent


class MainSuite
    extends FunSuite
    with LazyLogging
    with ForAllTestContainer
    with TestComponents
    with DatabaseSchema
    with MoviesSuite
    with ScreeningRoomsSuite
    with ScreeningTimesSuite
    with ReservationsSuite {

  self: FunSuite =>

  override val container = {
    val logConsumer = new Slf4jLogConsumer(logger.underlying)
    val container = PostgreSQLContainer()
    container.container.withLogConsumer(logConsumer)
    container
  }

  lazy val database = Database.forURL(container.jdbcUrl, user=container.username, password=container.password)
  val executionContext = globalExecutionContext
  val dateTimeFactory = userDateTimeFactory

  val moviesService = new MoviesService()
  val screeningRoomsService = new ScreeningRoomsService()
  val screeningTimesService = new ScreeningTimesService()
  val reservationsService = new ReservationsService()
  val ticketPricesService = new TicketPricesService()
}
