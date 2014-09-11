package util

import java.security.MessageDigest

import daos.UserDao
import models.{User, SessionDTO}
import play.api.cache.Cache
import play.api.db.slick._
import play.api.mvc.{Headers, AnyContent, Request}
import play.api.Play.current

object SessionUtilImpl extends SessionUtil {

	override def isCorrectReq[A](headers: Headers): Boolean = {
		headers.get("X-Requested-With") == Some("XMLHttpRequest") &&
			headers.get("Host") == Some("localhost:9000") &&
			headers.get("Origin") == Some("http://localhost:9000")
	}

	override def getSessionFromCache(sessionId: String): SessionDTO = {
		val opt = Cache.getAs[SessionDTO](sessionId)
		opt match {
			case None => throw new Exception("no session (old session)")
			case Some(s) => s
		}
	}

	override def createSession(user: User)(s: Session)(implicit userDao: UserDao): String = {
		checkUser(user, userDao)(s)
		val sessionId = HashUtil.hash
		val session = SessionDTO(user.id, sessionId)
		Cache.set(sessionId, session)
		sessionId
	}

	private def checkUser(user: User, userDao: UserDao)(s: Session) = {
		//UserDao.create(User("admin", encript("admin")))(s)
		val sameUser = userDao.getById(user.id)(s) match {
			case None => throw new Exception("user name fail")
			case Some(u) => u
		}
		require(sameUser.pass == HashUtil.hash(user.pass), "pass not match")
	}

	override def checkSession[A](request: Request[AnyContent])(implicit sessionUtil: SessionUtil): SessionDTO = {
		require(isCorrectReq(request.headers))
		val opt = request.session.get("sessinId")
		opt match {
			case None => throw new Exception("no session")
			case Some(s) => getSessionFromCache(s)
		}
	}
}