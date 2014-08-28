package controllers

import daos.{CustomerDao, FormSampleDao}
import models.{CustomerDTO, ErrorMessage, FormSampleDTO}
import play.api._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Request, Action, Controller}

//for slick DBSession
import play.api.db.slick._
import play.api.Play.current

object Sample extends Controller {

  def getTopSample(id: Long) = Action {
    Ok(preLogic(miniLogic()))
  }

  private def miniLogic():Session => JsValue = { implicit session: Session =>
    val result = FormSampleDTO(1, "success")
    Json.toJson(result)
  }

  def putTopSample(id: Long) = Action {request =>
    require(id >= 0)
    require(isCorrectReq(request))
    val jsValue = request.body.asJson.get
    val formDto = Json.fromJson[FormSampleDTO](jsValue).get
    Ok(preLogic(logic1(formDto)))
  }

//  def fromJson[T](request: Request[AnyContent]):T = {
//    val jsValue = request.body.asJson.get
//    Json.fromJson[T](jsValue).get
//  }

  private def logic1(form: FormSampleDTO):Session => JsValue = { implicit session: Session =>
    FormSampleDao.create(form)
    val formNew = FormSampleDao.getById(form.id+1).get
    Json.toJson(FormSampleDTO(formNew.id, "Hello " + formNew.formStr))
  }

  def upload = Action(parse.multipartFormData) { request =>
    request.body.file("file").map { picture =>
      import java.io.File
      val filename = picture.filename
      val contentType = picture.contentType
      picture.ref.moveTo(new File("tmp/" + filename))
      Ok(filename + contentType)
    }.getOrElse {
      Ok("miss")
    }
  }

  def getCustomer(id: Long) = Action {rs =>
    require(id >= 0)
    Ok(preLogic(logic2(id)))
  }

  private def logic2(id:Long):Session => JsValue = {implicit session =>
      CustomerDao.create(CustomerDTO(-1, "name"))
      CustomerDao.update(CustomerDTO(1, "update"))
      val customer = CustomerDao.searchByID(id).get
      val result = CustomerDTO(customer.id, customer.name)
      Json.toJson(result)
  }

  def preLogic(func:Session => JsValue):JsValue = DB.withSession {session=>
    try{
      func(session)
    } catch {
      // exception like this...
      //case e: TransactionException => Left("your post data is old")
      //case e: JdbcSQLException => Left(ErrorMessage("your sql is not good"))//transaction?
      //case e: JsResultException => Json.toJson(ErrorMessage("Bad request"))
      case e: Exception => Json.toJson(ErrorMessage("Internal server error"))
    }
  }

  def isCorrectReq[A](request : Request[A]) = {
    request.headers.get("X-Requested-With") == Some("XMLHttpRequest")&&
    request.headers.get("Host") == Some("localhost:9000")&&
    request.headers.get("Origin") == Some("http://localhost:9000")
  }

}
