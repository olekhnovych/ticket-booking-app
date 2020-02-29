package com.github.olekhnovych.ticketbooking.dto

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


case class CreateMovie(title: String)


trait CreateMovieJsonSupport extends SprayJsonSupport
    with DefaultJsonProtocol {

  implicit val createMovieFormat = jsonFormat1(CreateMovie)
}
