package controllers

import di.Production._
import play.api.mvc.{Action, Controller}
import services.{CsvService, SessionService}
import util.PlayFileUploadUtil

object CsvController extends Controller {

	def importCsv = Action { request =>
		SessionService.checkAuthorized(request)
		PlayFileUploadUtil.checkFileExist(request) match {
			case None => BadRequest("upload file not found")
			case Some(file) => Ok(CsvService.importCsv(file))
		}
	}

	def exportCsv(searchName: String) = Action { request =>
		SessionService.checkAuthorized(request)
		val file = CsvService.exportCsv(searchName)
		Ok.sendFile(
			content = file,
			fileName = _ => "sample.csv"
		)
	}
}