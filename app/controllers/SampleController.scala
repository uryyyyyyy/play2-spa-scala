package controllers

import models.FormSampleDTO
import play.api._
import play.api.db.slick._
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.{S3UploadService, SessionService, SampleService}
import di.Production._
import play.api.Play.current

object SampleController extends Controller {

	def getTopSample(id: Long) = Action { request =>
		SessionService.isGetAuthorized(request)
		val res = DB.withTransaction(SampleService.miniLogic(_))
		Ok(Json.toJson(res))
	}

	def putTopSample(id: Long) = Action { request =>
		require(id >= 0)
		SessionService.isPostAuthorized(request)
		val formDto = FormSampleDTO.fromJson(request.body.asJson)
		val formSample = DB.withTransaction(SampleService.logic1(_, formDto))
		Ok(Json.toJson(formSample))
	}

	def upload = Action(parse.multipartFormData) { request =>
		val str = S3UploadService.uploadToS3(request.body.file("file"))
		Ok(str)
	}

	def download(fileName: String) = Action { request =>
		SessionService.isGetAuthorized(request)
		val file = S3UploadService.downloadFromS3(fileName)
		Ok.sendFile(
			content = file,
			fileName = _ => fileName
		)
	}

	def getCustomer(id: Long) = Action { rs =>
		require(id >= 0)
		val c = DB.withSession(SampleService.logic2(_, id))
		Ok(Json.toJson(c))
	}
}