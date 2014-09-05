package util

import java.security.MessageDigest

import daos.UserDao
import models.{PRMUser, SessionDTO}
import play.api.cache.Cache
import play.api.db.slick._
import play.api.mvc.Request
import play.api.Play.current

object SessionUtil {

  def isCorrectReq[A](request : Request[A]):Boolean = {
    request.headers.get("X-Requested-With") == Some("XMLHttpRequest")&&
    request.headers.get("Host") == Some("localhost:9000")&&
    request.headers.get("Origin") == Some("http://localhost:9000")
  }

  def getSessionFromCache(sessionId: String):SessionDTO = {
    val opt = Cache.getAs[SessionDTO](sessionId)
    opt match{
      case None => throw new Exception("not found in cache")
      case Some(s) => s
    }
  }

  def checkSession[A](request : Request[A]):SessionDTO = {
    val opt = request.session.get("sessinId")
    opt match{
      case None => throw new Exception("no session")
      case Some(s) => getSessionFromCache(s)
    }
  }

  def checkUser(user: PRMUser) = DB.withSession {implicit session =>
    UserDao.create(PRMUser("admin", encript("admin")))
    val sameUser = UserDao.getByName(user.name) match{
      case None => throw new Exception("user name fail")
      case Some(u) => u
    }
    require(sameUser.pass == encript(user.pass), "pass not match")
  }

  def encript(s: String): String = {
    val md = MessageDigest.getInstance("SHA-1")
    md.update(s.getBytes)
    md.digest.foldLeft("") { (s, b) => s + "%02x".format(if(b < 0) b + 256 else b) }
  }

  def createSessionId:String = {
    java.util.UUID.randomUUID.toString
  }

  def createSession(user: PRMUser): String = {
    SessionUtil.checkUser(user)
    val sessionId = createSessionId
    val session = SessionDTO(user.name, sessionId)
    Cache.set(sessionId, session)
    sessionId
  }

}
