package models

import play.api.libs.json.Json

case class FormSampleDTO(id: Long, formStr: String)

object FormSampleDTO {
  //these implicit serve convert(Json <-> ScalaObject)
  implicit val writes = Json.writes[FormSampleDTO]
  implicit val reads = Json.reads[FormSampleDTO]
}