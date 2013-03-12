package models

import play.api.db.slick.Profile

case class Post(id:Option[Int], title:String, author:String, content:String)

case class PostForm(title:String, author:String, content:String) {
  def toPost:Post = Post(
    None,
    this.title,
    this.author,
    this.content
  )
}

trait PostComponent { this: Profile =>
  import profile.simple._

  object Posts extends Table[Post]("POST") {
    def id = column[Int]("id", O.AutoInc, O.PrimaryKey)
    def title = column[String]("title", O.NotNull)
    def author = column[String]("author", O.NotNull)
    def content = column[String]("content", O.NotNull)

    def * = id.? ~ title ~ author ~ content <> (Post, Post.unapply _)
  }

}