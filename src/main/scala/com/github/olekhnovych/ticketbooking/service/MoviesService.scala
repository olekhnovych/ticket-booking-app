package com.github.olekhnovych.ticketbooking.service

import com.github.olekhnovych.ticketbooking.{dao => dao, dto => dto}
import dao.DatabaseProfile.api._


trait MoviesServiceComponent {
  this: DatabaseComponent
      with ExecutionContextComponent =>

  val moviesService: MoviesService

  class MoviesService {
    def createMovie(createMovie: dto.CreateMovie) = {
      database.run {
        val daoMovie = dao.Movie(createMovie.title)
        (dao.movies.returning(dao.movies.map(_.id))) += daoMovie
      }.map(dto.JustId)
    }

    def deleteScreeningTime(movieId: dto.Id) =
      database.run(dao.movies.getById(movieId).delete)
  }
}
