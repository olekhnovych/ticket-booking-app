package com.github.olekhnovych.ticketbooking.dao

import DatabaseProfile.api._


case class ReservationSeat(reservationId: Id,
                           seatId: Id,
                           id: Option[Id]=None)


class ReservationSeats(tag: Tag) extends Table[ReservationSeat](tag, "reservation_seats") {
  def id = column[Id]("id", O.PrimaryKey, O.AutoInc)
  def reservationId  = column[Id]("reservation_id")
  def seatId  = column[Id]("seat_id")
  def * = (reservationId, seatId, id.?) <> (ReservationSeat.tupled, ReservationSeat.unapply)

  def reservation = foreignKey("reservation_seats_reservation_fkey", reservationId, reservations)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
  def seat = foreignKey("reservation_seats_seat_fkey", seatId, seats)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
  def uniqueSeat = index("reservation_seats_unique_seat_idx", (reservationId, seatId), unique=true)
}


object reservationSeats extends TableQuery(new ReservationSeats(_)) {
}
