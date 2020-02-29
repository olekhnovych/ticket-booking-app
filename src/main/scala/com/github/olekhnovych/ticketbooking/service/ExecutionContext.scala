package com.github.olekhnovych.ticketbooking.service

import scala.concurrent.ExecutionContext


trait ExecutionContextComponent {
  implicit val executionContext: ExecutionContext
}
