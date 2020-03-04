package com.github.olekhnovych.ticketbooking.dao

import org.joda.time.{DateTime, Period}

import monocle.Lens
import monocle.macros.GenLens

import DatabaseProfile.api._


case class ScreeningTime(startTime: DateTime,
                         duration: Period,
                         movieId: Id,
                         screeningRoomId: Id,
                         id: Option[Id]=None)


object ScreeningTimeLens {
  lazy val id: Lens[ScreeningTime, Option[Id]] = GenLens[ScreeningTime](_.id)
  lazy val movieId: Lens[ScreeningTime, Id] = GenLens[ScreeningTime](_.movieId)
}


class ScreeningTimes(tag: Tag) extends Table[ScreeningTime](tag, "screening_times") {
  def id = column[Id]("id", O.PrimaryKey, O.AutoInc)
  def startTime = column[DateTime]("start_time")
  def duration = column[Period]("duration")
  def movieId = column[Id]("movie_id")
  def screeningRoomId = column[Id]("screening_room_id")
  def * = (startTime, duration, movieId, screeningRoomId, id.?) <> (ScreeningTime.tupled, ScreeningTime.unapply)

  def movie = foreignKey("screening_times_movie_fkey", movieId, movies)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
  def screeningRoom = foreignKey("screening_times_screening_room_fkey", screeningRoomId, screeningRooms)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
}


object screeningTimes extends TableQuery(new ScreeningTimes(_)) {
  def getById(id: Id) =
    this.filter(_.id === id)
}
