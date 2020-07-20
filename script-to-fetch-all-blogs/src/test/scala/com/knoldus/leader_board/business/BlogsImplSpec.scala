package com.knoldus.leader_board.business

import java.sql.{Connection, Timestamp}

<<<<<<< HEAD:app/src/test/scala/com/knoldus/leader_board/business/BlogsImplSpec.scala
import com.knoldus.leader_board.infrastructure.{FetchBlogs, FetchBlogsImpl}
=======
>>>>>>> 214137affeb3d92feca09a0b7a2211dc3995ad4b:script-to-fetch-all-blogs/src/test/scala/com/knoldus/leader_board/business/BlogsImplSpec.scala
import com.knoldus.leader_board.{Blog, DatabaseConnection}
import com.typesafe.config.ConfigFactory
import org.mockito.{Mockito, MockitoSugar}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.wordspec.AnyWordSpecLike

class BlogsImplSpec extends AnyWordSpecLike with MockitoSugar with BeforeAndAfterEach {
  implicit val connection: Connection = DatabaseConnection.connection(ConfigFactory.load())
<<<<<<< HEAD:app/src/test/scala/com/knoldus/leader_board/business/BlogsImplSpec.scala
  val mockFetchBlogs: FetchBlogs = mock[FetchBlogsImpl]
  val mockURLResponse: URLResponse = mock[URLResponse]
  val blogs: Blogs = new BlogsImpl(mockFetchBlogs, mockURLResponse, ConfigFactory.load())
=======
  val mockURLResponse: URLResponse = mock[URLResponse]
  val blogs: Blogs = new BlogsImpl(mockURLResponse, ConfigFactory.load())
  val spyBlogs: Blogs = spy(blogs)
>>>>>>> 214137affeb3d92feca09a0b7a2211dc3995ad4b:script-to-fetch-all-blogs/src/test/scala/com/knoldus/leader_board/business/BlogsImplSpec.scala

  "Blogs" should {

    val blogData: String =
      """[{
        |  "id": 74399,
        |  "date": "2020-05-30T02:20:11",
        |  "date_gmt": "2020-05-29T20:50:11",
        |  "guid": {
        |   "rendered": "https:\/\/blog.knoldus.com\/?p=74399"
        |  },
        |  "modified": "2020-05-30T02:59:01",
        |  "modified_gmt": "2020-05-29T21:29:01",
        |  "slug": "realtime-supply-chains",
        |  "status": "publish",
        |  "type": "post",
        |  "link": "https:\/\/blog.knoldus.com\/realtime-supply-chains\/",
        |  "title": {
        |   "rendered": "Realtime Supply Chains"
        |  },
        |  "_embedded": {
        |   "author": [{
        |    "id": 66,
        |    "name": "Ram Indukuri",
        |    "url": "",
        |    "description": "As an Engineer, I help customers in architecting platforms using Spark, Mesos, Cassandra, Kafka (And their commercial versions).  As a partner, I guide customers in setting up the organization, processes and build top-notch teams that solve complex problems or deliver digital transformation.\r\n\r\nMy interests and expertise are in Mathematics, Machine learning, Microservices, Linked data, distributed cloud infrastructure, Real-time enterprise data integration.",
        |    "link": "https:\/\/blog.knoldus.com\/author\/ramindukuri\/",
        |    "slug": "ramindukuri"
        |   }]
        |  }
        |}]""".stripMargin

<<<<<<< HEAD:app/src/test/scala/com/knoldus/leader_board/business/BlogsImplSpec.scala
    "return latest blogs from API" in {
      when(mockFetchBlogs.fetchMaxBlogPublicationDate)
        .thenReturn(Option(Timestamp.from(Instant.parse("2020-04-13T14:56:40Z"))))

      when(mockURLResponse.getBlogResponse(ConfigFactory.load.getString("urlForLatestBlogs"),
        "2020-05-30 02:20:11"))
        .thenReturn(blogData)

      val date = "2020-05-30 02:20:11"
=======
    "return all blogs from API" in {
      MockitoSugar.doReturn(4).when(spyBlogs).getTotalNoOfPosts
      val date = "2020-04-14 12:35:30"
>>>>>>> 214137affeb3d92feca09a0b7a2211dc3995ad4b:script-to-fetch-all-blogs/src/test/scala/com/knoldus/leader_board/business/BlogsImplSpec.scala
      val publishedOn = Timestamp.valueOf(date)

      val listOfBlogs = List(Blog(
        Option(70547),
        Option("pankajchaudhary5"),
        publishedOn,
        Option("Get a Look on Key Rust Crates for WebAssembly"))
      )
      Mockito.doAnswer(_ => listOfBlogs).when(spyBlogs).getAllBlogs(1)
      assert(spyBlogs.getAllBlogsFromAPI == listOfBlogs)
    }

    "return list of blogs" in {
      val date = "2020-05-30 02:20:11"
      val publishedOn = Timestamp.valueOf(date)
      assert(blogs.getListOfBlogs(blogData) == List(Blog(
        Option(74399),
        Option("ramindukuri"),
        publishedOn,
<<<<<<< HEAD:app/src/test/scala/com/knoldus/leader_board/business/BlogsImplSpec.scala
        Option("Realtime Supply Chains")))

      assert(blogs.getLatestBlogsFromAPI == List())
=======
        Option("Realtime Supply Chains"))))
    }

    "return list of blogs if last page is the first page" in {
      assert(blogs.getAllBlogs(1) == List())
>>>>>>> 214137affeb3d92feca09a0b7a2211dc3995ad4b:script-to-fetch-all-blogs/src/test/scala/com/knoldus/leader_board/business/BlogsImplSpec.scala
    }

    "return list of blogs if last page is not first page" in {
      when(mockURLResponse.getResponse(ConfigFactory.load.getString("urlForAllBlogs")))
        .thenReturn(blogData)
      val date = "2020-05-30 02:20:11"
      val publishedOn = Timestamp.valueOf(date)
<<<<<<< HEAD:app/src/test/scala/com/knoldus/leader_board/business/BlogsImplSpec.scala

      assert(blogs.getListOfLatestBlogs(blogData) == List(Blog(
=======
      val listOfBlogs = List(Blog(
>>>>>>> 214137affeb3d92feca09a0b7a2211dc3995ad4b:script-to-fetch-all-blogs/src/test/scala/com/knoldus/leader_board/business/BlogsImplSpec.scala
        Option(74399),
        Option("ramindukuri"),
        publishedOn,
        Option("Realtime Supply Chains")))
      assert(blogs.getAllBlogs(2) == List())
    }
  }
}
