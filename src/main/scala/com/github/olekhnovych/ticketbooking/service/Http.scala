package com.github.olekhnovych.ticketbooking

import akka.http.scaladsl.Http

import pureconfig.ConfigSource
import pureconfig.generic.auto._

import com.github.olekhnovych.ticketbooking.api._
import com.github.olekhnovych.ticketbooking.service._


case class HttpServerConfig(interface: String, port: Int)
case class ApplicationConfig(http: HttpServerConfig)


trait HttpComponent {
  this: ActorSystemComponent
      with ApiComponent =>

  val applicationConfig = ConfigSource.default.loadOrThrow[ApplicationConfig]

  def httpBindAndHandle() = {
    Http().bindAndHandle(apiRoute, applicationConfig.http.interface, applicationConfig.http.port)
  }
}
