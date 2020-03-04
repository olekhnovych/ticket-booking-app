package com.github.olekhnovych.ticketbooking

import org.joda.time.DateTime

import com.github.olekhnovych.ticketbooking.service._


object userDateTimeFactory extends DateTimeFactory {
  var userDateTime: DateTime = DateTime.now()

  def set(dateTime: DateTime) = {
    userDateTime = dateTime
  }

  def now() = userDateTime
}
