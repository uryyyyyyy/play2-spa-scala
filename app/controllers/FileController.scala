package controllers

import di.Production._
import play.api.mvc.{Action, Controller}
import services.{FileService, SessionService}
import util.PlayFileUploadUtil

object FileController extends Controller {

	def upload = Action { request =>
		SessionService.checkAuthorized(request)
		PlayFileUploadUtil.checkFileExist(request) match {
			case None => BadRequest("upload file not found")
			case Some(file) => Ok(FileService.uploadToS3(file))
		}
	}

	def download(fileName: String) = Action { request =>
		SessionService.checkAuthorized(request)
		val file = FileService.downloadFromS3(fileName)
		Ok.sendFile(
			content = file,
			fileName = _ => fileName
		)
	}

	def list = Action { request =>
		SessionService.checkAuthorized(request)
		FileService.list
		Ok("OK")
	}
}