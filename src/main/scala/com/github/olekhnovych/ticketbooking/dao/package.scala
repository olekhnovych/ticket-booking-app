package com.github.olekhnovych.ticketbooking


package object dao {
  type Id = Int


  import DatabaseProfile.api._

  val schemas = screeningTimes.schema ++
                movies.schema ++
                seats.schema ++
                screeningRooms.schema ++
                reservations.schema ++
                reservationSeats.schema

  val dropIfExistsStatements = schemas.dropIfExists.statements.map(statement => s"${statement} cascade").toList
  val createStatements = schemas.create.statements.toList
  val truncateStatements = schemas.truncate.statements.toList
}
