package com.github.olekhnovych.ticketbooking.dao

import org.joda.time.DateTime

import DatabaseProfile.api._


case class Reservation(personName: String,
                       personSurname: String,
                       totalAmountToPay: Double,
                       expirationTime: DateTime,
                       confirmed: Boolean,
                       confirmationExpirationTime: DateTime,
                       screeningTimeId: Id,
                       id: Option[Id]=None)


class Reservations(tag: Tag) extends Table[Reservation](tag, "reservations") {
  def id = column[Id]("id", O.PrimaryKey, O.AutoInc)
  def personName = column[String]("person_name")
  def personSurname = column[String]("person_surname")
  def amountToPay = column[Double]("amount_to_pay")
  def expirationTime = column[DateTime]("expiration_time")
  def confirmed = column[Boolean]("confirmed")
  def confirmationExpirationTime = column[DateTime]("confirmation_expiration_time")
  def screeningTimeId  = column[Id]("screening_time_id")
  def * = (personName, personSurname, amountToPay, expirationTime, confirmed, confirmationExpirationTime, screeningTimeId, id.?) <> (Reservation.tupled, Reservation.unapply)

  def screeningTime = foreignKey("reservations_screening_time_fkey", screeningTimeId, screeningTimes)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
  def uniquePerson = index("reservations_unique_person_idx", (personName, personSurname, screeningTimeId), unique=true)
}


object reservations extends TableQuery(new Reservations(_)) {
  def getById(id: Id) =
    this.filter(_.id === id)
}
