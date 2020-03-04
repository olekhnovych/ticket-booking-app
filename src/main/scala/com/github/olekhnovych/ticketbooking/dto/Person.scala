package com.github.olekhnovych.ticketbooking.dto

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


case class Person(name: String,
                  surname: String)


trait PersonJsonSupport extends SprayJsonSupport
    with DefaultJsonProtocol {

  implicit val personFormat = jsonFormat2(Person)
}
