package models

import play.api.libs.json.{JsValue, Json}

case class PRMUser(name: String, pass: String)

object PRMUser {
  //these implicit serve convert(Json <-> ScalaObject)
  implicit val writes = Json.writes[PRMUser]
  implicit val reads = Json.reads[PRMUser]

  def fromJson(jsValue:JsValue):PRMUser = {
    val opt = Json.fromJson[PRMUser](jsValue).asOpt
    opt match {
      case None => throw new Exception("cannot convert")
      case Some(u) => u
    }
  }
}