package models

import play.api.libs.json.Json

case class ErrorMessage(errMsg: String)

object ErrorMessage {
  //these implicit serve convert(Json <-> ScalaObject)
  implicit val writes = Json.writes[ErrorMessage]
  implicit val reads = Json.reads[ErrorMessage]
}