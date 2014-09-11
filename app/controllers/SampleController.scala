package controllers

import models.FormSampleDTO
import play.api._
import play.api.db.slick._
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.SampleService
import util.{SessionUtilImpl, S3Uploader}
import di.Production._
import play.api.Play.current

object SampleController extends Controller {

  def getTopSample(id: Long) = Action {
	  val res = DB.withTransaction(SampleService.miniLogic(_))
	  Ok(Json.toJson(res))
  }

  def putTopSample(id: Long) = Action {request =>
    require(id >= 0)
    SessionUtilImpl.checkSession(request)
    val formDto = FormSampleDTO.fromJson(request.body.asJson)
    val formSample = DB.withTransaction(SampleService.logic1(_, formDto))
    Ok(Json.toJson(formSample))
  }

  def upload = Action(parse.multipartFormData) { request =>
    request.body.file("file").map { postedFile =>
      import java.io.File
      val filename = postedFile.filename
      val contentType = postedFile.contentType
      postedFile.ref.moveTo(new File("tmp/" + filename))
      S3Uploader.post
      Ok(filename + contentType)
    }.getOrElse {
      Ok("miss")
    }
  }

  def getCustomer(id: Long) = Action {rs =>
    require(id >= 0)
    val c = DB.withSession(SampleService.logic2(_, id))
    Ok(Json.toJson(c))
  }
}
