package models

import play.api.db.slick.Profile

case class Tag(id:Option[Int], name:String)

trait TagComponent { this: Profile =>
  import profile.simple._

  object Tags extends Table[Tag]("TAG") {
    def id = column[Int]("id", O.AutoInc)
    def name = column[String]("name", O.NotNull)

    def * = id.? ~ name <> (Tag, Tag.unapply _)
  }
}

