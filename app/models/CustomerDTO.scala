package models

import play.api.libs.json.{JsValue, Json}

case class CustomerDTO(id: Long, name: String)

object CustomerDTO {
  //these implicit serve convert(Json <-> ScalaObject)
  implicit val writes = Json.writes[CustomerDTO]
  implicit val reads = Json.reads[CustomerDTO]

  def fromJson(jsValue:JsValue):CustomerDTO = {
    val opt = Json.fromJson[CustomerDTO](jsValue).asOpt
    opt match {
      case None => throw new Exception("cannot convert")
      case Some(u) => u
    }
  }
}