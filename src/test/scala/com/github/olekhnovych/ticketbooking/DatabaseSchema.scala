package com.github.olekhnovych.ticketbooking

import org.scalatest.{Suite, BeforeAndAfterEach}

import com.github.olekhnovych.ticketbooking.service._
import dao.DatabaseProfile.api._


trait DatabaseSchema
    extends BeforeAndAfterEach
    with AsyncResult {

  this: Suite
      with DatabaseComponent
      with ExecutionContextComponent =>

  override def beforeEach() {
    result(database.run(dao.schemas.create))
    super.beforeEach()
  }

  override def afterEach() {
    try {
      super.afterEach()
    }
    finally {
      result(database.run(dao.schemas.dropIfExists))
    }
  }
}
