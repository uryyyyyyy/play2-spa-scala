package controllers
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.SessionService
import di.Production._
import util.SessionUtilImpl
import play.api.db.slick._
import play.api.Play.current

object ConfigController extends Controller {

	def config = Action { request =>
		val bodyJson = request.body.asJson
		val header = request.headers
		val sessionId: String = DB("master").withSession(SessionService.checkUserIsCorrect(_, header, bodyJson))
		Ok("OK").withSession("sessinId" -> sessionId)
	}

	def isAuthorized = Action { request =>
		val sessionDto = SessionUtilImpl.checkSession(request)
		Ok(Json.toJson(sessionDto))
	}
}
