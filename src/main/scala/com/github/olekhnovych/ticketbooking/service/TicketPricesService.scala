package com.github.olekhnovych.ticketbooking.service

import org.joda.time.{DateTime, DateTimeConstants}

import com.github.olekhnovych.ticketbooking.{dao => dao, dto => dto}
import dao.DatabaseProfile.api._


trait TicketPricesServiceComponent {
  this: DatabaseComponent
      with DateTimeFactoryComponent =>

  val ticketPricesService: TicketPricesService

  class TicketPricesService {

    //TODO keep in database?
    lazy val pricesMap: Map[dto.TicketType.Value, Double] = Map(dto.TicketType.adult -> 25.0,
                                                                dto.TicketType.student -> 18.0,
                                                                dto.TicketType.child -> 12.50,
                                                                )

    def getTicketPrice(ticketType: dto.TicketType.Value) = {
      val basePrice = pricesMap.get(ticketType).get

      val now = dateTimeFactory.now()
      val isWeekend = (now.getDayOfWeek() == DateTimeConstants.FRIDAY && now.getHourOfDay() >= 14) ||
                      (now.getDayOfWeek() == DateTimeConstants.SATURDAY) ||
                      (now.getDayOfWeek() == DateTimeConstants.SUNDAY && now.getHourOfDay() < 23)

      val price = if(isWeekend)
                    basePrice + 4.0
                  else
                    basePrice

      //TODO voucher

      price
    }
  }
}
