package controllers

import play.api.mvc.{Action, Controller}
import services.{FileService, SessionService}
import di.Production._

object FileController extends Controller {

	def upload = Action(parse.multipartFormData) { request =>
		val str = FileService.uploadToS3(request.body.file("file"))
		Ok(str)
	}

	def download(fileName: String) = Action { request =>
		SessionService.isGetAuthorized(request)
		val file = FileService.downloadFromS3(fileName)
		Ok.sendFile(
			content = file,
			fileName = _ => fileName
		)
	}

	def list = Action { request =>
		SessionService.isGetAuthorized(request)
		FileService.list
		Ok("OK")
	}
}