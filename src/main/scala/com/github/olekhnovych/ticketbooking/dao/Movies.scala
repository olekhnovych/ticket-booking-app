package com.github.olekhnovych.ticketbooking.dao

import DatabaseProfile.api._


case class Movie(title: String,
                 id: Option[Id]=null)


class Movies(tag: Tag) extends Table[Movie](tag, "movies") {
  def id = column[Id]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def * = (title, id.?) <> (Movie.tupled, Movie.unapply)
}


object movies extends TableQuery(new Movies(_)) {
  def getById(id: Id) =
    this.filter(_.id === id)
}
