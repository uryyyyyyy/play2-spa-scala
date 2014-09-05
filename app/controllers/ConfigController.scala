package controllers
import models._
import play.api._
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import util.SessionUtil

object ConfigController extends Controller {

  def config = Action {request =>
    require(SessionUtil.isCorrectReq(request))
    val jsValue = request.body.asJson.get
    val user = PRMUser.fromJson(jsValue)

    val sessionId = SessionUtil.createSession(user)
    Ok("OK").withSession("sessinId" -> sessionId)
  }

  def lookSession = Action { request =>
    val sessionDto = SessionUtil.checkSession(request)
    Ok(Json.toJson(sessionDto))
  }

}
