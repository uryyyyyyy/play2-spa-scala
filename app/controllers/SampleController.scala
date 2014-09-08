package controllers

import daos.{CustomerDao, FormSampleDao}
import models.{CustomerDTO, FormSampleDTO}
import play.api._
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.SampleService
import util.{SessionUtil, S3Uploader}

object SampleController extends Controller {

  def getTopSample(id: Long) = Action {
    Ok(Json.toJson(SampleService.miniLogic()))
  }

  def putTopSample(id: Long) = Action {request =>
    require(id >= 0)
    SessionUtil.checkSession(request)
    val formDto = FormSampleDTO.fromJson(request.body.asJson)
    val formSample = SampleService.logic1(formDto)
    Ok(Json.toJson(formSample))
  }

  def upload = Action(parse.multipartFormData) { request =>
    request.body.file("file").map { postedFile =>
      import java.io.File
      val filename = postedFile.filename
      val contentType = postedFile.contentType
      postedFile.ref.moveTo(new File("tmp/" + filename))
      S3Uploader.put(new File("tmp/" + filename))
      Ok(filename + contentType)
    }.getOrElse {
      Ok("miss")
    }
  }

  def getCustomer(id: Long) = Action {rs =>
    require(id >= 0)
    Ok(Json.toJson(SampleService.logic2(id, CustomerDao)))
  }
}
