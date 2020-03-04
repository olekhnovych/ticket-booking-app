package com.github.olekhnovych.ticketbooking.dao

import com.github.tminglei.slickpg._


trait DatabaseProfile extends ExPostgresProfile
    with PgArraySupport
    with PgDate2Support
    with PgRangeSupport
    with PgDateSupportJoda {

  def pgjson = "jsonb"
  override val api = DatabaseAPI

  object DatabaseAPI extends API
      with ArrayImplicits
      with DateTimeImplicits
      with JodaDateTimeImplicits
      with RangeImplicits
  {
    implicit val strListTypeMapper = new SimpleArrayJdbcType[String]("text").to(_.toList)
  }
}


object DatabaseProfile extends DatabaseProfile
