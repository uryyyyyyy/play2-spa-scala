package util

import java.security.MessageDigest

import daos.UserDao
import models.{User, SessionDTO}
import play.api.cache.Cache
import play.api.db.slick._
import play.api.mvc.{AnyContent, Request}
import play.api.Play.current

object SessionUtilImpl extends SessionUtil{

  override def isCorrectReq[A](request : Request[A]):Boolean = {
    request.headers.get("X-Requested-With") == Some("XMLHttpRequest")&&
    request.headers.get("Host") == Some("localhost:9000")&&
    request.headers.get("Origin") == Some("http://localhost:9000")
  }

  override def getSessionFromCache(sessionId: String):SessionDTO = {
    val opt = Cache.getAs[SessionDTO](sessionId)
    opt match{
      case None => throw new Exception("no session (old session)")
      case Some(s) => s
    }
  }

  override def createSession(user: User)(s:Session): String = {
    checkUser(user)(s)
    val sessionId = createSessionId
    val session = SessionDTO(user.name, sessionId)
    Cache.set(sessionId, session)
    sessionId
  }

  private def checkUser(user: User)(s:Session) = {
    //UserDao.create(User("admin", encript("admin")))(s)
    val sameUser = UserDao.getByName(user.name)(s) match{
      case None => throw new Exception("user name fail")
      case Some(u) => u
    }
    require(sameUser.pass == encript(user.pass), "pass not match")
  }

  private def encript(s: String): String = {
    val md = MessageDigest.getInstance("SHA-1")
    md.update(s.getBytes)
    md.digest.foldLeft("") { (s, b) => s + "%02x".format(if(b < 0) b + 256 else b) }
  }

  private def createSessionId:String = {
    java.util.UUID.randomUUID.toString
  }

}
