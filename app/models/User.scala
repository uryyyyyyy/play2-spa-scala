package models

import play.api.libs.json.{JsValue, Json}

case class User(id: String, pass: String)

object User {
	//these implicit serve convert(Json <-> ScalaObject)
	implicit val writes = Json.writes[User]
	implicit val reads = Json.reads[User]

	def fromJson(jsValue: JsValue): User = {
		val opt = Json.fromJson[User](jsValue).asOpt
		opt match {
			case None => throw new Exception("cannot convert")
			case Some(u) => u
		}
	}
}