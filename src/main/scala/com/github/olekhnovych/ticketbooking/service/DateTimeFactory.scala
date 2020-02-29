package com.github.olekhnovych.ticketbooking.service

import org.joda.time.DateTime


trait DateTimeFactory {
  def now(): DateTime
}


object realtimeDateTimeFactory extends DateTimeFactory {
  def now() = DateTime.now()
}


trait DateTimeFactoryComponent {
  val dateTimeFactory: DateTimeFactory
}
