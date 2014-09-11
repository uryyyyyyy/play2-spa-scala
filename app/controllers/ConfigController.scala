package controllers
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.SessionService
import di.Production._
import util.SessionUtilImpl

object ConfigController extends Controller {

  def config = Action {request =>
    val sessionId: String = SessionService.checkUserIsCorrect(request.headers, request.body.asJson)
    Ok("OK").withSession("sessinId" -> sessionId)
  }

  def isAuthorized = Action { request =>
    val sessionDto = SessionUtilImpl.checkSession(request)
    Ok(Json.toJson(sessionDto))
  }

}
