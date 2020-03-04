package com.github.olekhnovych.ticketbooking

import org.scalatest.{FunSuite, Matchers}

import com.github.olekhnovych.ticketbooking.service._
import dao.DatabaseProfile.api._


trait ScreeningRoomsSuite
    extends Matchers
    with AsyncResult {

  this: FunSuite
      with DatabaseComponent
      with ScreeningRoomsServiceComponent
      with ExecutionContextComponent =>

  test("create screenig room") {
    result (
      for(screeningRoomId <- screeningRoomsService.createScreeningRoom(dto.CreateScreeningRoom("room-a", 2, 3)).map(_.id);
          daoScreeningRooms <- database.run(dao.screeningRooms.result);
          daoSeats <- database.run(dao.seats.result)
      )
      yield {
        daoScreeningRooms should have size 1
        daoScreeningRooms should contain (dao.ScreeningRoom("room-a", Some(screeningRoomId)))

        daoSeats should have size 6
        daoSeats.map(dao.SeatLens.id.set(None)) should contain allOf (dao.Seat(1, 1, screeningRoomId),
                                                                      dao.Seat(1, 3, screeningRoomId),
                                                                      dao.Seat(2, 1, screeningRoomId),
                                                                      dao.Seat(2, 3, screeningRoomId))

        daoSeats.map(dao.SeatLens.id.set(None)) should contain noneOf (dao.Seat(1, 0, screeningRoomId),
                                                                       dao.Seat(1, 4, screeningRoomId))
      }
    )
  }
}
