package com.knoldus.leader_board

import akka.actor.ActorSystem
import com.knoldus.leader_board.application.{AllTimeDataOnAPI, AllTimeDataOnAPIImpl}
import com.knoldus.leader_board.business._
import com.knoldus.leader_board.infrastructure._
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._

object DriverApp extends App {
  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  val config: Config = ConfigFactory.load()

  val readKnolder = new ReadKnolderImpl(config)
  val readBlog = new ReadBlogImpl(config)
  val readAllTime = new ReadAllTimeImpl(config)
  val writeAllTime = new WriteAllTimeImpl(config)
  val numberOfBlogsPerKnolder: NumberOfBlogsPerKnolder = new NumberOfBlogsPerKnolderImpl(readKnolder: ReadKnolder,
    readBlog: ReadBlog, readAllTime: ReadAllTime, writeAllTime: WriteAllTime)
  val overallReputation: OverallReputation = new OverallReputationImpl(readAllTime, config)
  val readAllTimeReputation: ReadAllTimeReputation = new ReadAllTimeReputationImpl(config)
  val reputationPerKnolder: ReputationPerKnolder = new ReputationPerKnolderImpl(overallReputation, readAllTimeReputation)
  val writeAllTimeReputation: WriteAllTimeReputation = new WriteAllTimeReputationImpl(config, overallReputation)
  val allTimeDataOnAPI: AllTimeDataOnAPI = new AllTimeDataOnAPIImpl(overallReputation, readAllTimeReputation, config)

  val task1 = new Runnable {
    override def run(): Unit = {
      val knolderBlogCounts = numberOfBlogsPerKnolder.getKnolderBlogCount
      writeAllTime.insertAllTimeData(knolderBlogCounts)
      writeAllTime.updateAllTimeData(knolderBlogCounts)
    }
  }

  val task2 = new Runnable {
    override def run(): Unit = {
      val knolderReputations = reputationPerKnolder.getKnolderReputation
      writeAllTimeReputation.insertAllTimeReputationData(knolderReputations)
      writeAllTimeReputation.updateAllTimeReputationData(knolderReputations)
    }
  }
  system.scheduler.scheduleWithFixedDelay(0.seconds, 30.seconds)(task1)
  system.scheduler.scheduleWithFixedDelay(0.seconds, 30.seconds)(task2)
  allTimeDataOnAPI.displayAllTimeDataOnAPI
}
