package com.github.olekhnovych.ticketbooking

import org.scalatest.{FunSuite, Matchers}

import org.joda.time.{DateTime, Period}

import com.github.olekhnovych.ticketbooking.service._
import dao.DatabaseProfile.api._


trait ReservationsSuite
    extends Matchers
    with AsyncResult {

  this: FunSuite
      with DatabaseComponent
      with MoviesServiceComponent
      with ScreeningRoomsServiceComponent
      with ScreeningTimesServiceComponent
      with ReservationsServiceComponent
      with ExecutionContextComponent =>

  def initBackground() =
    for(movieIdA <- moviesService.createMovie(dto.CreateMovie("movie-a")).map(_.id);
        movieIdB <- moviesService.createMovie(dto.CreateMovie("movie-b")).map(_.id);

        screeningRoomIdA <- screeningRoomsService.createScreeningRoom(dto.CreateScreeningRoom("room-a", 2, 5)).map(_.id);
        screeningRoomIdB <- screeningRoomsService.createScreeningRoom(dto.CreateScreeningRoom("room-b", 2, 5)).map(_.id);

        screeningTimeIdA <- screeningTimesService.createScreeningTime(dto.CreateScreeningTime(new DateTime(2020,1,1,12,0,0), Period.hours(2), movieIdA, screeningRoomIdA)).map(_.id)
    )
    yield screeningTimeIdA

  test("create reservation before screening") {
    userDateTimeFactory.set(new DateTime(2020,1,1,11,0,0))   // 1 hour before
    result (
      for(screeningTimeIdA <- initBackground();
          seats <- reservationsService.getSeatsByScreeningTime(screeningTimeIdA);
          reservationResult <- reservationsService.createReservation(dto.CreateReservation(dto.Person("name-a", "surname-a"), screeningTimeIdA, List(dto.ReservationSeat(dto.TicketType.adult, seats.head.id))))
      )
      yield {
        val setConfirmUrl = dto.ReservationResultLens.confirmUrl.set(dto.ConfirmReservationResource(0))
        setConfirmUrl(reservationResult) should be (dto.ReservationResult(25.0, new DateTime(2020,1,1,11,45,0), dto.ConfirmReservationResource(0)))
      }
    )
  }

  test("create reservation after screening") {
    userDateTimeFactory.set(new DateTime(2020,1,1,12,10,0))   // 10 minutes after
    val serviceError = intercept[dto.ServiceError] {
      result (
        for(screeningTimeIdA <- initBackground();
            seats <- reservationsService.getSeatsByScreeningTime(screeningTimeIdA);
            reservationResult <- reservationsService.createReservation(dto.CreateReservation(dto.Person("name-a", "surname-a"), screeningTimeIdA, List(dto.ReservationSeat(dto.TicketType.adult, seats.head.id))))
        )
        yield ()
      )
    }
    serviceError.errorMessage.code should be ("INVALID_RESERVATION_TIME")
  }

  //TODO test other cases
}
