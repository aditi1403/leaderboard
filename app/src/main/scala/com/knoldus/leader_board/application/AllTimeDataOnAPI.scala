package com.knoldus.leader_board.application

import akka.http.scaladsl.Http

import scala.concurrent.Future

trait AllTimeDataOnAPI {
  def pushAllTimeDataOnAPI: Future[Http.ServerBinding]
}
