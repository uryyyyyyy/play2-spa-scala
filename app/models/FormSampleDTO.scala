package models

import play.api.libs.json.{JsValue, Json}

case class FormSampleDTO(id: Long, formStr: String)

object FormSampleDTO {
	//these implicit serve convert(Json <-> ScalaObject)
	implicit val writes = Json.writes[FormSampleDTO]
	implicit val reads = Json.reads[FormSampleDTO]

	def fromJson(opt: Option[JsValue]): FormSampleDTO = {
		val jsValue = opt.get
		val opt2 = Json.fromJson[FormSampleDTO](jsValue).asOpt
		opt2 match {
			case None => throw new Exception("cannot convert")
			case Some(u) => u
		}
	}
}