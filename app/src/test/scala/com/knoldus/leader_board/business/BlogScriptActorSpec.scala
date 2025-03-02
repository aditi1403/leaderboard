package com.knoldus.leader_board.business

import java.sql.Connection

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import com.knoldus.leader_board.infrastructure._
import com.knoldus.leader_board.{DatabaseConnection, ExecuteBlogsScript}
import com.typesafe.config.ConfigFactory
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class BlogScriptActorSpec extends TestKit(ActorSystem("BlogScriptActorSpec")) with ImplicitSender
  with AnyWordSpecLike with Matchers with BeforeAndAfterAll with MockitoSugar {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val mockBlogs: Blogs = mock[BlogsImpl]
  val mockStoreBlogs: StoreBlogs = mock[StoreBlogsImpl]
  val mockAllTimeReputation: AllTimeReputation = mock[AllTimeReputationImpl]
  val mockWriteAllTimeReputation: WriteAllTimeReputation = mock[WriteAllTimeReputationImpl]
  val mockMonthlyReputation: MonthlyReputation = mock[MonthlyReputationImpl]
  val mockWriteMonthlyReputation: WriteMonthlyReputation = mock[WriteMonthlyReputationImpl]
  val mockQuarterlyReputation: QuarterlyReputation = mock[QuarterlyReputationImpl]
  val mockKnolderMonthlyContribution= mock[KnolderMonthlyContributionImpl]
  val mockWriteMonthlyContribution= mock[WriteMonthlyContribution]
  val mockWriteQuarterlyReputation: WriteQuarterlyReputation = mock[WriteQuarterlyReputationImpl]
  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "Blog Script Actor" must {
    "not do anything with incorrect message" in {
      val probe = TestProbe()
      val mockActorRef = probe.ref
      val scriptActor = system.actorOf(Props(new BlogScriptActor(mockStoreBlogs, mockBlogs,mockActorRef)))
      probe watch scriptActor
      probe.send(scriptActor, "display reputation")
      probe.expectMsg(InvalidMessage)
    }
    "execute blogs script" in {
      val probe = TestProbe.apply()
      val mockActorRef = probe.ref
      val scriptActor = system.actorOf(Props(new BlogScriptActor(
        mockStoreBlogs, mockBlogs,mockActorRef)))
      probe watch scriptActor
      probe.send(scriptActor, ExecuteBlogsScript)
      probe.expectMsg(StoredBlogs)
    }
  }
}
