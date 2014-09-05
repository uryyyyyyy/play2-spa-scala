package controllers

import daos.{UserDao, CustomerDao, FormSampleDao}
import models.{PRMUser, CustomerDTO, ErrorMessage, FormSampleDTO}
import play.api._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Request, Action, Controller}
import play.api.db.slick._
import play.api.Play.current
import util.S3Uploader

import scala.slick.jdbc.JdbcBackend

object ConfigController extends Controller {


  private def miniLogic():Session => JsValue = { implicit session: Session =>
    val result = FormSampleDTO(1, "success")
    Json.toJson(result)
  }

  def config = Action {request =>
    require(isCorrectReq(request))
    val jsValue = request.body.asJson.get
    val user = Json.fromJson[PRMUser](jsValue).get

    if(checkUser(user)){
      Ok("OK").withSession(
        "connected" -> user.name)
    }else{
      Ok(Json.toJson(ErrorMessage("error")))
    }
  }

  def create(user: PRMUser) = DB.withSession {implicit session =>
    UserDao.create(user)
  }

  def lookSession = Action { request =>
    create(PRMUser("admin", "gegege"))
    request.session.get("connected").map { user =>
      Ok("Hello " + user)
    }.getOrElse {
      Unauthorized("Oops, you are not connected")
    }
  }

  def encript(s: String): String = {
    s + "aaa"
  }

  private def checkUser(user: PRMUser):Boolean = DB.withSession {implicit session =>
    UserDao.create(PRMUser("admin", "admin"))
    UserDao.getByName(user.name) match{
      case None => false
      case Some(u) => u.pass == encript(user.pass)
    }
  }

  def isCorrectReq[A](request : Request[A]) = {
    request.headers.get("X-Requested-With") == Some("XMLHttpRequest")&&
    request.headers.get("Host") == Some("localhost:9000")&&
    request.headers.get("Origin") == Some("http://localhost:9000")
  }

}
