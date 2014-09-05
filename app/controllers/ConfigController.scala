package controllers

import daos.UserDao
import models._
import play.api._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Request, Action, Controller}
import play.api.db.slick._
import play.api.Play.current
import util.S3Uploader
import play.api.cache.Cache

import scala.slick.jdbc.JdbcBackend

object ConfigController extends Controller {

  def createSessionId:String = {
    "randomString"
  }

  def config = Action {request =>
    require(isCorrectReq(request))
    val jsValue = request.body.asJson.get
    val user = Json.fromJson[PRMUser](jsValue).get

    if(checkUser(user)){
      val sessionId = createSessionId
      Cache.set(sessionId, user)
      Ok("OK").withSession(
        "sessinId" -> sessionId)
    }else{
      Ok(Json.toJson(ErrorMessage("error")))
    }
  }

  def create(user: PRMUser) = DB.withSession {implicit session =>
    UserDao.create(user)
  }

  def lookSession = Action { request =>
    request.session.get("sessinId").map { sessionId =>
      val user = Cache.getAs[PRMUser](sessionId).get
      Ok(Json.toJson(SessionDTO(user.name, sessionId)))
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
