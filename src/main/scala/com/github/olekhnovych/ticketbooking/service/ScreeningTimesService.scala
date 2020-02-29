package com.github.olekhnovych.ticketbooking.service

import org.joda.time.DateTime

import com.github.olekhnovych.ticketbooking.{dao => dao, dto => dto}
import dao.DatabaseProfile.api._


trait ScreeningTimesServiceComponent {
  this: DatabaseComponent with ExecutionContextComponent =>

  val screeningTimesService: ScreeningTimesService

  class ScreeningTimesService {
    def createScreeningTime(screeningTime: dto.CreateScreeningTime) = {
      database.run {
        val daoScreeningTime = dao.ScreeningTime(screeningTime.start,
                                                 screeningTime.duration,
                                                 screeningTime.movieId,
                                                 screeningTime.screeningRoomId)

        (dao.screeningTimes.returning(dao.screeningTimes.map(_.id))) += daoScreeningTime
      }.map(dto.JustId)
    }

    def deleteScreeningTime(screeningTimeId: dto.Id) =
      database.run(dao.screeningTimes.getById(screeningTimeId).delete)

    def findScreeningTimesByTimeInterval(from: DateTime, to: DateTime) = {
      database.run {
        dao.screeningTimes.filter(st => st.startTime.between(from, to))
          .join(dao.movies).on(_.movieId === _.id)
          .sortBy(row => (row._2.title, row._1.startTime))
          .result
      }.map(_.map{case (screeningTime, movie) =>
              dto.ScreeningTime(screeningTime.id.get,
                                screeningTime.startTime,
                                screeningTime.duration,
                                dto.Movie(movie.title))
            })
      }
  }
}
