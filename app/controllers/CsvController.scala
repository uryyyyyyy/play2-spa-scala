package controllers

import play.api.db.slick._
import play.api.mvc.{Action, Controller}
import services.{CsvService, SessionService}
import di.Production._
import play.api.Play.current

object CsvController extends Controller {

	def importCsv = Action(parse.multipartFormData) { request =>
		CsvService.importScv(request.body.file("file"))
		Ok("ok")
	}

	def exportCsv(fileName: String) = Action { request =>
		SessionService.isGetAuthorized(request)
		val file = DB.withTransaction(CsvService.exportScv(_, fileName))
		Ok.sendFile(
			content = file,
			fileName = _ => fileName
		)
	}
}