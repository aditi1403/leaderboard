package com.knoldus.leader_board.business

import java.sql.{Connection, Timestamp}

import com.knoldus.leader_board.{DatabaseConnection, Knolx}
import com.typesafe.config.ConfigFactory
import org.mockito.ArgumentMatchersSugar.any
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike

class KnolxImplSpec extends AnyWordSpecLike with MockitoSugar with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
  val mockURLResponse: URLResponse = mock[URLResponse]
  val knolx: Knolxs = new KnolxImpl(mockURLResponse, ConfigFactory.load())

  "Knolx" should {

    val knolxData: String =
      """[{
        |	"email": "mukesh.kumar@knoldus.com",
        |	"knolxCount": 4,
        |	"knolxDetails": [{
        |		"dateOfSession": "Mon Jan 19 11:49:09 IST 1970",
        |		"id": "ab3c6981-9964-46e2-adcd-64154120c1dc",
        |		"title": "Reactive Microservices"
        |	}, {
        |		"dateOfSession": "Mon Jan 19 15:11:46 IST 1970",
        |		"id": "4cb67c8f-941b-4860-ba4e-a7e7f497768d",
        |		"title": "Delta Lake"
        |	}],
        |	"userId": "8694bdb6-3bf0-4c86-a443-6e631b10562b"
        |}]""".stripMargin

    "return latest knolx from API" in {

      when(mockURLResponse.getResponse(any))
        .thenReturn(knolxData)

      val listOfKnolx = List(
        Knolx(
          Option("ab3c6981-9964-46e2-adcd-64154120c1dc"),
          Option("mukesh.kumar@knoldus.com"),
          Option(Timestamp.valueOf("1970-01-19 11:49:09.0")),
          Option("Reactive Microservices")),
        Knolx(
          Option("4cb67c8f-941b-4860-ba4e-a7e7f497768d"),
          Option("mukesh.kumar@knoldus.com"),
          Option(Timestamp.valueOf("1970-01-19 15:11:46.0")),
          Option("Delta Lake"))
      )

      assert(knolx.getAllKnolxDetails == listOfKnolx)
    }

    "return empty list when an invalid response receive from api" in {
      when(mockURLResponse.getResponse(any))
        .thenReturn("""[]""")


      assert(knolx.getAllKnolxDetails == List())
    }

    "parse knolx json string" in {
      val listOfKnolx = List(
        Knolx(
          Option("ab3c6981-9964-46e2-adcd-64154120c1dc"),
          Option("mukesh.kumar@knoldus.com"),
          Option(Timestamp.valueOf("1970-01-19 11:49:09.0")),
          Option("Reactive Microservices")),
        Knolx(
          Option("4cb67c8f-941b-4860-ba4e-a7e7f497768d"),
          Option("mukesh.kumar@knoldus.com"),
          Option(Timestamp.valueOf("1970-01-19 15:11:46.0")),
          Option("Delta Lake"))
      )

      assert(knolx.parseAllKnolxJsonData(knolxData) == listOfKnolx)
    }
  }
}
