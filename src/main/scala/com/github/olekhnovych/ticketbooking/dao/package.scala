package com.github.olekhnovych.ticketbooking


package object dao {
  type Id = Int


  import DatabaseProfile.api._

  val schemas = screeningRooms.schema ++
                movies.schema ++
                seats.schema ++
                screeningTimes.schema ++
                reservations.schema ++
                reservationSeats.schema

  val dropIfExistsStatements = schemas.dropIfExists.statements.map(statement => s"${statement} cascade").toList
  val createStatements = schemas.create.statements.toList
  val truncateStatements = schemas.truncate.statements.map(statement => s"${statement} cascade").toList
}
