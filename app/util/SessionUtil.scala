package util

import daos.UserDao
import models.{User, SessionDTO}
import play.api.db.slick._
import play.api.mvc.{AnyContent, Request, Headers}

trait SessionUtil {

	def isCorrectReq[A](request: Headers): Boolean

	def createSession(user: User)(s: Session)(implicit userDao: UserDao): String

	def getSessionFromCache(sessionId: String): SessionDTO

	def isPostAuthorized[A](request: Request[AnyContent])(implicit sessionUtil: SessionUtil): SessionDTO

	def isGetAuthorized(request: Request[AnyContent])(implicit sessionUtil: SessionUtil): SessionDTO
}
