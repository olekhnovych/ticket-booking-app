package com.github.olekhnovych.ticketbooking.dao

import DatabaseProfile.api._

import monocle.Lens
import monocle.macros.GenLens


case class ScreeningRoom(name: String,
                         id: Option[Id]=None)


object ScreeningRoomLens {
  lazy val id: Lens[ScreeningRoom, Option[Id]] = GenLens[ScreeningRoom](_.id)
}


class ScreeningRooms(tag: Tag) extends Table[ScreeningRoom](tag, "screening_rooms") {
  def id = column[Id]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def * = (name, id.?) <> (ScreeningRoom.tupled, ScreeningRoom.unapply)
}


object screeningRooms extends TableQuery(new ScreeningRooms(_)) {
  def getById(id: Id) =
    this.filter(_.id === id)
}
