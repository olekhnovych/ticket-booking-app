package com.github.olekhnovych.ticketbooking.dao

import DatabaseProfile.api._


case class ScreeningRoom(name: String,
                         id: Option[Id]=None)


class ScreeningRooms(tag: Tag) extends Table[ScreeningRoom](tag, "screening_rooms") {
  def id = column[Id]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def * = (name, id.?) <> (ScreeningRoom.tupled, ScreeningRoom.unapply)
}


object screeningRooms extends TableQuery(new ScreeningRooms(_)) {
  def getById(id: Id) =
    this.filter(_.id === id)
}
