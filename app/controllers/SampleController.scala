package controllers

import di.Production._
import models.FormSampleDTO
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.{SampleService, SessionService}

object SampleController extends Controller {

	def getTopSample(id: Long) = Action { request =>
		require(id >= 0)
		SessionService.checkAuthorized(request)
		val res = SampleService.miniLogic
		Ok(Json.toJson(res))
	}

	def putTopSample(id: Long) = Action { request =>
		require(id >= 0)
		SessionService.checkAuthorized(request)
		val formDto = FormSampleDTO.fromJson(request.body.asJson)
		val formSample = SampleService.logic1(formDto)
		Ok(Json.toJson(formSample))
	}

	def getCustomer(id: Long) = Action { request =>
		require(id >= 0)
		SessionService.checkAuthorized(request)
		val c = SampleService.logic2(id)
		Ok(Json.toJson(c))
	}
}