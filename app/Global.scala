import models.ErrorMessage
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import play.api.mvc.Results._
import scala.concurrent.Future

object Global extends GlobalSettings {

  override def doFilter(next: EssentialAction): EssentialAction = {
    Filters(super.doFilter(next), LoggerFilter)
  }

  override def onError(request: RequestHeader, ex: Throwable) = {
//    val jsValue = ex match{
//      case e: Exception => Json.toJson(ErrorMessage(e.getMessage))
//      case e: IllegalArgumentException => Json.toJson(ErrorMessage(e.getMessage))
//    }
    Logger.debug(ex.getMessage)
    Future.successful(InternalServerError(Json.toJson(ErrorMessage(ex.getMessage))))
  }

  override def onStart(app : Application) = {
    Logger.info("on start")
  }

}