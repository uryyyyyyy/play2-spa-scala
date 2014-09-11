package services

import models.User
import play.api.db.slick._
import play.api.libs.json.JsValue
import play.api.mvc.Headers
import util.SessionUtil
import di.Production._

object SessionService {

	def checkUserIsCorrect(session: Session, header: Headers, json: Option[JsValue])
	                      (implicit sessionUtil: SessionUtil): String = {
		require(sessionUtil.isCorrectReq(header))
		val jsValue = json.get
		val user = User.fromJson(jsValue)
		val sessionId = sessionUtil.createSession(user)(session)
		sessionId
	}
}