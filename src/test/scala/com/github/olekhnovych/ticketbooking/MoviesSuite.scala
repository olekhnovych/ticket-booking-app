package com.github.olekhnovych.ticketbooking

import org.scalatest.{FunSuite, Matchers}

import com.github.olekhnovych.ticketbooking.service._
import dao.DatabaseProfile.api._


trait MoviesSuite
    extends Matchers
    with AsyncResult {

  this: FunSuite
      with DatabaseComponent
      with MoviesServiceComponent
      with ExecutionContextComponent =>

  test("create movie") {
    result (
      for(movieId <- moviesService.createMovie(dto.CreateMovie("movie-a")).map(_.id);
          daoMovies <- database.run(dao.movies.result)
      )
      yield {
        daoMovies should have size 1
        daoMovies should contain (dao.Movie("movie-a", Some(movieId)))
      }
    )
  }
}
