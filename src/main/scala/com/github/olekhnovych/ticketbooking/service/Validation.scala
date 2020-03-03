package com.github.olekhnovych.ticketbooking.service

import org.joda.time.{DateTime, Period}

import com.github.olekhnovych.ticketbooking.{dao => dao, dto => dto}


trait Validation {
  def validateOneSeatAtLeast(createReservation: dto.CreateReservation): Unit =
    if (!(createReservation.reservationSeats.size >= 1))
      throw dto.ServiceError.conflict("ONE_SEAT_AT_LEAST", "There should be one seat at least.")

  def validateAtLeast15minBeforeStart(screeningTime: dao.ScreeningTime, now: DateTime): Unit =
    if (!(now.isBefore(screeningTime.startTime.minus(Period.minutes(15)))))
      throw dto.ServiceError.conflict("INVALID_RESERVATION_TIME", "Seats should be booked at least 15 minutes before the screening starts.")

  def validateSeatsBelogToScreeningRoom(createReservation: dto.CreateReservation, screeningRoomSeats: Seq[dao.Seat]): Unit =
    if (!createReservation.reservationSeats.map(_.seatId).forall(screeningRoomSeats.map(_.id.get).contains(_)))
      throw dto.ServiceError.conflict("INVALID_SEAT", "Invalid seat id.")

  def validateSeatsAreNotAlreadyReserved(createReservation: dto.CreateReservation, reservedSeats: Seq[dao.Seat]) =
    if (createReservation.reservationSeats.map(_.seatId).forall(reservedSeats.map(_.id.get).contains(_)))
      throw dto.ServiceError.conflict("SEAT_ALREADY_RESERVED", "Seat is already reserved.")

  def validateNoSinglePlaceBetweenReserved(reservedSeats: Seq[dao.Seat], userReservedSeats: Seq[dao.Seat]) = {
    val noSinglePlace = (reservedSeats ++ userReservedSeats).groupBy(_.row).values
      .forall(row => row.isEmpty || {
                val sortedSeats = row.map(_.seat).sorted
                sortedSeats.tail.zip(sortedSeats.init).map{case (next, prev) => next - prev}.forall(_ != 2)
              })

    if(!noSinglePlace)
      throw dto.ServiceError.conflict("SINGLE_PLACE_LEFT", "There cannot be single place left over in row between two reserved.")
  }
}
