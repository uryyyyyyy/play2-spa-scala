package controllers

import di.Production._
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.SessionService


object ConfigController extends Controller {

	def config = Action { request =>
		val bodyJson = request.body.asJson
		val header = request.headers
		val sessionId = SessionService.checkUserIsCorrect(header, bodyJson)
		Ok("OK").withSession("sessionId" -> sessionId)
	}

	def isAuthorized = Action { request =>
		val sessionDto = SessionService.checkAuthorized(request)
		Ok(Json.toJson(sessionDto))
	}
}