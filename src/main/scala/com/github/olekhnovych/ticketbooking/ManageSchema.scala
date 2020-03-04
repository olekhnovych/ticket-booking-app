package com.github.olekhnovych.ticketbooking

import scala.concurrent.ExecutionContext.Implicits.{global => globalExecutionContext}
import scala.concurrent.Await
import scala.concurrent.duration._

import scala.collection.JavaConverters._

import slick.jdbc.PostgresProfile.api.Database
import dao.DatabaseProfile.api._

import com.github.olekhnovych.ticketbooking.service._


trait ManageSchemaComponents extends
    DatabaseComponent
    with ExecutionContextComponent


object ManageSchemaRegistry extends ManageSchemaComponents {
  val database = Database.forConfig("main")
  implicit val executionContext = globalExecutionContext
}


object ManageSchema extends App {
  val actionType = args(0)

  val action = actionType match {
    case "create" => dao.schemas.create
    case "dropIfExists" => dao.schemas.dropIfExists
    case "truncate" => dao.schemas.truncate
  }

  Await.result(ManageSchemaRegistry.database.run(action), Duration.Inf);
}
