package controllers
import play.api._
import play.api.libs.json.Json
import play.api.mvc.{AnyContent, Request, Action, Controller}
import services.SessionService
import di.Production._

object ConfigController extends Controller {

  def config = Action {request =>
    val sessionId: String = SessionService.checkUserIsCorrect(request)
    Ok("OK").withSession("sessinId" -> sessionId)
  }

  def lookSession = Action { request =>
    val sessionDto = SessionService.checkSession(request)
    Ok(Json.toJson(sessionDto))
  }

}
