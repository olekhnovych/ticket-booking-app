package com.github.olekhnovych.ticketbooking

import org.scalatest.{FunSuite, Matchers}

import org.joda.time.{DateTime, Period}

import com.github.olekhnovych.ticketbooking.service._
import dao.DatabaseProfile.api._


trait ScreeningTimesSuite
    extends Matchers
    with AsyncResult {

  this: FunSuite
      with DatabaseComponent
      with MoviesServiceComponent
      with ScreeningRoomsServiceComponent
      with ScreeningTimesServiceComponent
      with ExecutionContextComponent =>

  test("create screening time") {
    result (
      for(movieId <- moviesService.createMovie(dto.CreateMovie("movie-a")).map(_.id);
          screeningRoomId <- screeningRoomsService.createScreeningRoom(dto.CreateScreeningRoom("room-a", 2, 3)).map(_.id);
          screeningTimeId <- screeningTimesService.createScreeningTime(dto.CreateScreeningTime(new DateTime(2020,1,1,14,0,0), Period.hours(2), movieId, screeningRoomId)).map(_.id);
          daoScreeningTimes <- database.run(dao.screeningTimes.result)
      )
      yield {
        daoScreeningTimes should have size 1
        daoScreeningTimes should contain (dao.ScreeningTime(new DateTime(2020,1,1,14,0,0), Period.hours(2), movieId, screeningRoomId, Some(screeningTimeId)))
      }
    )
  }

  test("find screening time") {
    result (
      for(movieId <- moviesService.createMovie(dto.CreateMovie("movie-a")).map(_.id);
          screeningRoomId <- screeningRoomsService.createScreeningRoom(dto.CreateScreeningRoom("room-a", 2, 3)).map(_.id);
          screeningTimeId <- screeningTimesService.createScreeningTime(dto.CreateScreeningTime(new DateTime(2020,1,1,14,0,0), Period.hours(2), movieId, screeningRoomId)).map(_.id);
          screeningTimes <- screeningTimesService.findScreeningTimesByTimeInterval(new DateTime(2020,1,1,14,0,0), new DateTime(2020,1,1,15,0,0));
          emptyScreeningTimes <- screeningTimesService.findScreeningTimesByTimeInterval(new DateTime(2020,1,1,15,0,0), new DateTime(2020,1,1,16,0,0))
      )
      yield {
        screeningTimes should have size 1
        screeningTimes should contain (dto.ScreeningTime(screeningTimeId, new DateTime(2020,1,1,14,0,0), Period.hours(2), dto.Movie("movie-a")))

        emptyScreeningTimes shouldBe empty
      }
    )
  }
}
