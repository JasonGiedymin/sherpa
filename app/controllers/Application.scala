package controllers

import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.db.slick.DB
import models._

//stable imports to use play.api.Play.current outside of objects:
import models.current.dao._
import models.current.dao.profile.simple._

object Application extends Controller {
  import play.api.Play.current

  def index = Action {
    DB.withSession{ implicit session =>
      Ok(views.html.index(Query(Posts).list))
    }
  }

  val postForm = Form(
    mapping(
      "title" -> text(),
      "author" -> text(),
      "content" -> text()
    )(PostForm.apply)(PostForm.unapply)
  )
  
  def insert = Action { implicit request =>
    val post = postForm.bindFromRequest.get.toPost
    DB.withSession{ implicit session =>
      Posts.insert(post)
    }

    Redirect(routes.Application.index())
  }
  
}
