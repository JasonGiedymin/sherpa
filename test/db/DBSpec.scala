package db

import org.specs2.mutable._

import play.api.db.slick.DB
import play.api.test._
import play.api.test.Helpers._
import models._

/**
  * test the database here
  */
class DBSpec extends Specification {

  "DB" should {
    "work with Posts" in new WithApplication {
      val dao = new DAO(DB.driver)

      import dao._ //import all our database Tables
      import dao.profile.simple._ //import specific database methods
      DB.withSession{ implicit session =>

        val testPosts = Seq(
          Post(None, "post1", "me", "hello world"),
          Post(None, "post2", "me", "hello world"),
          Post(None, "post3", "me", "hello world")
        )

        val expectedPosts = Seq(
          Post(Some(1), "post1", "me", "hello world"),
          Post(Some(2), "post2", "me", "hello world"),
          Post(Some(3), "post3", "me", "hello world")
        )

        Posts.insertAll( testPosts: _*)
        Query(Posts).list mustEqual expectedPosts
        Query(Posts).list.length mustEqual 3
      }
    }

    "work with Tags" in new WithApplication {
      val dao = new DAO(DB.driver)

      import dao._ //import all our database Tables
      import dao.profile.simple._ //import specific database methods
      DB.withSession{ implicit session =>

        val testTags = Seq(
          Tag(None, "tag1"),
          Tag(None, "tag2"),
          Tag(None, "tag3")
        )

        val expectedTags = Seq(
          Tag(Some(1), "tag1"),
          Tag(Some(2), "tag2"),
          Tag(Some(3), "tag3")
        )

        Tags.insertAll( testTags: _*)
        Query(Tags).list mustEqual expectedTags
        Query(Tags).list.length mustEqual 3
      }
    }

    "work with Posts & Tag" in new WithApplication {
      val dao = new DAO(DB.driver)

      import dao._ //import all our database Tables
      import dao.profile.simple._ //import specific database methods
      DB.withSession{ implicit session =>

        // We will be using post1 later
        val post1 = Post(Some(1), "post1", "me", "hello world1")

        val testPosts = Seq(
          post1,
          Post(Some(2), "post2", "me", "hello world2"),
          Post(Some(3), "post3", "me", "hello world3")
        )

        val testTags = Seq(
          Tag(Some(1), "tag1"),
          Tag(Some(2), "tag2"),
          Tag(Some(3), "tag3"),
          Tag(Some(4), "tag4")
        )

        val postId: Int = post1.id getOrElse(1)
        val testPostsTags = Seq(
          PostTag(postId, 1),
          PostTag(postId, 2),
          PostTag(postId, 3)
        )

        Tags.insertAll( testTags: _* )
        Posts.insertAll( testPosts: _* )
        PostsTags.insertAll( testPostsTags: _* )
        Query(PostsTags).list.length mustEqual 3

        // Once inserted, lets make sure we can use it

        // Test that we can obtain 3 posts via the postId
        Query(PostsTags).filter(_.postId === postId).list.length mustEqual 3

        // Create a join
        val ptJoin = for {
          pt <- PostsTags if pt.postId === postId
          t <- Tags if t.id === pt.tagId
          p <- Posts if p.id === pt.postId
        } yield (p.title, t.name)
        //ptJoin foreach println

        // postId should have three tags assigned to it
        ptJoin.sortBy(_._2).map(_._2).list mustEqual(List("tag1", "tag2", "tag3"))

      }
    }

    "select the correct testing db settings by default" in new WithApplication(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
      play.api.db.slick.DB.withSession{ implicit session =>
        session.conn.getMetaData.getURL must startWith("jdbc:h2:mem:play-test")
      }
    }

    "use the correct db settings when specified" in new WithApplication {
      play.api.db.slick.DB("specific").withSession{ implicit session =>
        session.conn.getMetaData.getURL must equalTo("jdbc:h2:mem:veryspecialindeed")
      }
    }

    "use the default db settings when no other possible options are available" in new WithApplication {
      play.api.db.slick.DB.withSession{ implicit session =>
        session.conn.getMetaData.getURL must equalTo("jdbc:h2:mem:play")
      }
    }
  }

}
