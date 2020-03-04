package com.github.olekhnovych.ticketbooking.dto

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


case class Movie(title: String)


trait MovieJsonSupport extends SprayJsonSupport
    with DefaultJsonProtocol {
  implicit val movieFormat = jsonFormat1(Movie)
}
