package com.github.olekhnovych.ticketbooking.service

import com.github.olekhnovych.ticketbooking.{dao => dao, dto => dto}
import dao.DatabaseProfile.api._


trait ScreeningRoomsServiceComponent {
  this: DatabaseComponent with ExecutionContextComponent =>

  val screeningRoomsService: ScreeningRoomsService

  class ScreeningRoomsService {
    def createScreeningRoom(createScreeningRoom: dto.CreateScreeningRoom) = {
      database.run {
        for(screeningRoomId <- {
              val daoScreeningRoom = dao.ScreeningRoom(createScreeningRoom.name)
              (dao.screeningRooms.returning(dao.screeningRooms.map(_.id))) += daoScreeningRoom
            };
            _ <- {
              val daoSeats = for(row <- 1 to createScreeningRoom.rowsNumber;
                                 seat <- 1 to createScreeningRoom.seatsNumber)
                             yield dao.Seat(row, seat, screeningRoomId)
              dao.seats ++= daoSeats
            }
        )
        yield dto.JustId(screeningRoomId)
      }
    }

    def deleteScreeningRoom(screeningRoomId: dto.Id) =
      database.run(dao.screeningRooms.getById(screeningRoomId).delete)
  }
}
