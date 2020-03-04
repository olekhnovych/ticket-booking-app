package com.github.olekhnovych.ticketbooking

import org.scalatest.Suite

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._


trait AsyncResult {
  this: Suite =>

  def result[T](future: Future[T]) = Await.result(future, Duration.Inf)
}
