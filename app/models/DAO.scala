package models

import slick.driver.ExtendedProfile
import play.api.db.slick.Profile
import play.api.db.slick.DB

class DAO(override val profile: ExtendedProfile) extends Profile
  with TagComponent
  with PostComponent
  with PostTagComponent

object current {
  val dao = new DAO(DB.driver(play.api.Play.current))
}
