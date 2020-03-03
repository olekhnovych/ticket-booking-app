package com.github.olekhnovych.ticketbooking.service

import org.joda.time.{DateTime, Period}

import com.github.olekhnovych.ticketbooking.{dao => dao, dto => dto}
import dao.DatabaseProfile.api._


trait ReservationsServiceComponent extends Validation {
  this: DatabaseComponent
      with ExecutionContextComponent
      with DateTimeFactoryComponent
      with TicketPricesServiceComponent =>

  val reservationsService: ReservationsService

  class ReservationsService {
    def getSreeningRoomSeats(screeningTimeId: dto.Id) =
      dao.screeningTimes.getById(screeningTimeId)
        .join(dao.screeningRooms).on(_.screeningRoomId === _.id)
        .join(dao.seats).on(_._2.id === _.screeningRoomId)
        .map(_._2)
        .result

    def getReservedSeats(screeningTimeId: dto.Id) =
      dao.screeningTimes.getById(screeningTimeId)
        .join(dao.reservations).on(_.id === _.screeningTimeId)
        .join(dao.reservationSeats).on(_._2.id === _.reservationId)
        .join(dao.seats).on(_._2.seatId === _.id)
        .map(_._2)
        .result

    def getSeatsByScreeningTime(screeningTimeId: dto.Id) =
      database.run {
        (for(screeningRoomSeats <- getSreeningRoomSeats(screeningTimeId);
             reservedSeats <- getReservedSeats(screeningTimeId)
         ) yield {
           val reservedSeatsSet = reservedSeats.map(_.id.get).toSet
           screeningRoomSeats.map(seat => {
                                    val reserved = reservedSeatsSet.contains(seat.id.get)
                                    dto.Seat(seat.id.get, seat.row, seat.seat, reserved)
                                  })
         }).transactionally
      }

    def createReservation(createReservation: dto.CreateReservation) = {
      val now = dateTimeFactory.now

      validateOneSeatAtLeast(createReservation)

      database.run {
        (for(screeningTime <- dao.screeningTimes.getById(createReservation.screeningTimeId).result.head;
             screeningRoomSeats <- getSreeningRoomSeats(createReservation.screeningTimeId);
             reservedSeats <- getReservedSeats(createReservation.screeningTimeId);
             userReservedSeats <- dao.seats.getByIds(createReservation.seatIds).result;

             reservation <- {
               validateAtLeast15minBeforeStart(screeningTime, now)
               validateSeatsBelogToScreeningRoom(createReservation, screeningRoomSeats)
               validateSeatsAreNotAlreadyReserved(createReservation, reservedSeats)
               validateNoSinglePlaceBetweenReserved(reservedSeats, userReservedSeats)

               val expirationTime = screeningTime.startTime.minus(Period.minutes(15))           //TODO is it correct?
               val confirmationExpirationTime = now.plus(Period.minutes(15))

               val totalAmount = createReservation.ticketTypes.map(ticketPricesService.getTicketPrice _).sum[Double]

               val person = createReservation.person
               val daoReservation = dao.Reservation(person.name, person.surname, totalAmount, expirationTime, false, confirmationExpirationTime, createReservation.screeningTimeId)
               dao.reservations.returning(dao.reservations) += daoReservation
             };
             _ <- {
               val daoReservationSeats = createReservation.seatIds.map(dao.ReservationSeat(reservation.id.get, _))
               dao.reservationSeats ++= daoReservationSeats
             })
             yield {
               val confirmReservationResource = dto.ConfirmReservationResource(reservation.id.get)
               dto.ReservationResult(reservation.totalAmountToPay, reservation.expirationTime, confirmReservationResource)
             }
        ).transactionally
      }
    }

    def confirmReservation(reservationId: dto.Id) =
      database.run {
        dao.reservations.getById(reservationId).map(_.confirmed).update(true)
      }.map(x => ())

    def deleteExpiredReservations() = {
      val now = dateTimeFactory.now
      database.run {
        dao.reservations.filter(reservation => reservation.expirationTime < now ||
                                              (reservation.confirmationExpirationTime < now && !reservation.confirmed)).delete
      }
    }
  }
}
