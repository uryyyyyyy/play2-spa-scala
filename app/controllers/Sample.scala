package controllers

import daos.{CustomerDao, FormSampleDao}
import models.{CustomerDTO, FormSampleDTO}
import play.api._
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import play.api.db.slick._
import play.api.Play.current
import util.{SessionUtil, S3Uploader}

object Sample extends Controller {

  def getTopSample(id: Long) = Action {
    Ok(Json.toJson(miniLogic()))
  }

  private def miniLogic():FormSampleDTO = DB.withSession{implicit session =>
    FormSampleDTO(1, "success")
  }

  def putTopSample(id: Long) = Action {request =>
    require(id >= 0)
    SessionUtil.isCorrectReq(request)
    val formDto = FormSampleDTO.fromJson(request.body.asJson)
    val formSample = logic1(formDto)
    Ok(Json.toJson(formSample))
  }

  private def logic1(form: FormSampleDTO):FormSampleDTO = DB.withSession{implicit  session =>
    FormSampleDao.create(form)
    val opt = FormSampleDao.getById(form.id+1)
    val formNew = opt match {
      case None => throw new Exception("please retry")
      case Some(u) => u
    }
    FormSampleDTO(formNew.id, "Hello " + formNew.formStr)
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
    Ok(Json.toJson(logic2(id)))
  }

  private def logic2(id:Long):CustomerDTO = DB.withSession {implicit session =>
      CustomerDao.create(CustomerDTO(-1, "name"))
      CustomerDao.update(CustomerDTO(1, "update"))
      val customer = CustomerDao.searchByID(id).get
      CustomerDTO(customer.id, customer.name)
  }
}
