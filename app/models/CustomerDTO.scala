package models

import play.api.libs.json.Json

case class CustomerDTO(id: Long, name: String)

object CustomerDTO {
  //these implicit serve convert(Json <-> ScalaObject)
  implicit val writes = Json.writes[CustomerDTO]
  implicit val reads = Json.reads[CustomerDTO]
}