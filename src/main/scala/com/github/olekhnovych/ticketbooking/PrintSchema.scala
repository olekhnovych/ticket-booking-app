package com.github.olekhnovych.ticketbooking

import slick.jdbc.PostgresProfile.api._


object PrintSchema extends App {
  dao.dropIfExistsStatements.foreach(statement => println(s"${statement};"))
  dao.createStatements.foreach(statement => println(s"${statement};"))
}
