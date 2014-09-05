package controllers

import daos.{CustomerDao, FormSampleDao}
import models.{PRMUser, CustomerDTO, ErrorMessage, FormSampleDTO}
import play.api._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Request, Action, Controller}
import play.api.db.slick._
import play.api.Play.current
import util.S3Uploader

object ConfigController extends Controller {


  private def miniLogic():Session => JsValue = { implicit session: Session =>
    val result = FormSampleDTO(1, "success")
    Json.toJson(result)
  }

  def config = Action {request =>
    require(isCorrectReq(request))
    val jsValue = request.body.asJson.get
    val user = Json.fromJson[PRMUser](jsValue).get
    if(true){
      Ok("OK").withSession(
        "connected" -> user.name)
    }else{
      Ok(preLogic(logic1(user)))
    }
  }

  def lookSession = Action { request =>
    request.session.get("connected").map { user =>
      Ok("Hello " + user)
    }.getOrElse {
      Unauthorized("Oops, you are not connected")
    }
  }

  private def logic1(user: PRMUser):Session => JsValue = { session: Session =>

    //configuration check

    Json.toJson(user)
  }

  private def logic2(id:Long):Session => JsValue = {implicit session =>
      CustomerDao.create(CustomerDTO(-1, "name"))
      CustomerDao.update(CustomerDTO(1, "update"))
      val customer = CustomerDao.searchByID(id).get
      val result = CustomerDTO(customer.id, customer.name)
      Json.toJson(result)
  }

  //this method provide slick-Session
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
