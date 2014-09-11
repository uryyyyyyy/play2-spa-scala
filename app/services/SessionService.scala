package services

import models.{SessionDTO, User}
import play.api.db.slick._
import play.api.mvc.{AnyContent, Request}
import util.SessionUtil
import play.api.Play.current

object SessionService {

  def checkUserIsCorrect(request: Request[AnyContent])(implicit sessionUtil: SessionUtil): String =
    DB("master").withSession{session:Session =>
    require(sessionUtil.isCorrectReq(request))
    val jsValue = request.body.asJson.get
    val user = User.fromJson(jsValue)
    val sessionId = sessionUtil.createSession(user)(session)
    sessionId
  }

  def checkSession[A](request : Request[A])(implicit sessionUtil: SessionUtil):SessionDTO = {
    val opt = request.session.get("sessinId")
    opt match{
      case None => throw new Exception("no session")
      case Some(s) => sessionUtil.getSessionFromCache(s)
    }
  }

}
