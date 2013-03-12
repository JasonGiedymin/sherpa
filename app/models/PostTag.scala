package models

import play.api.db.slick.Profile

case class PostTag(postId:Int, tagId:Int)

trait PostTagComponent
  extends PostComponent // so we can use fk
  with TagComponent // so we can use fk
{ this: Profile =>
  import profile.simple._

  object PostsTags extends Table[PostTag]("POST_TAG") {
    def postId = column[Int]("postId")
    def tagId = column[Int]("tagId")

    def * = postId ~ tagId <> (PostTag, PostTag.unapply _)

    def pk = primaryKey("pk_post_tag", postId ~ tagId)
    def post = foreignKey("fk_post_tag_postid", postId, Posts)(_.id)
    def tag = foreignKey("fk_post_tag_tagid", tagId, Tags)(_.id)
  }
}
