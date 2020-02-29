package com.github.olekhnovych.ticketbooking.service

import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile


trait DatabaseComponent {
  val database: PostgresProfile.backend.DatabaseDef
}
