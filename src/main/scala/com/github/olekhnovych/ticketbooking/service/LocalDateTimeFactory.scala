package com.github.olekhnovych.ticketbooking.service

import org.joda.time.LocalDateTime


trait LocalDateTimeFactory {
  def now(): LocalDateTime
}


object realtimeLocalDateTimeFactory extends LocalDateTimeFactory {
  def now() = LocalDateTime.now()
}


trait LocalDateTimeFactoryComponent {
  val localDateTimeFactory: LocalDateTimeFactory
}
