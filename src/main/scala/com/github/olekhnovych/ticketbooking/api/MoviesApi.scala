package com.github.olekhnovych.ticketbooking.api

import com.github.olekhnovych.ticketbooking.service._
import com.github.olekhnovych.ticketbooking.{dto => dto}

import akka.http.scaladsl.server.Directives._


trait MoviesApiComponent extends
    dto.JustIdJsonSupport
    with dto.CreateMovieJsonSupport {

  this: MoviesServiceComponent =>

  def moviesApiRoute =
    path("movies") {
      post {
        entity(as[dto.CreateMovie]) { movie =>
          complete(moviesService.createMovie(movie))
        }
      }
    }
}
