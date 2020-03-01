package com.github.olekhnovych.ticketbooking.dao

import DatabaseProfile.api._


case class Seat(row: Int,
                seat: Int,
                screeningRoomId: Id,
                id: Option[Id]=None)


class Seats(tag: Tag) extends Table[Seat](tag, "seats") {
  def id = column[Id]("id", O.PrimaryKey, O.AutoInc)
  def row = column[Int]("row")
  def seat = column[Int]("seat")
  def screeningRoomId = column[Id]("screening_room_id")
  def * = (row, seat, screeningRoomId, id.?) <> (Seat.tupled, Seat.unapply)

  def screeningRoom = foreignKey("seats_screening_room_fkey", screeningRoomId, screeningRooms)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
}


object seats extends TableQuery(new Seats(_)) {
  def getByIds(ids: Seq[Id]) =
    this.filter(_.id.inSet(ids))
}
