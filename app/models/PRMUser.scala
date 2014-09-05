package models

import play.api.libs.json.Json

case class PRMUser(name: String, pass: String)

object PRMUser {
  //these implicit serve convert(Json <-> ScalaObject)
  implicit val writes = Json.writes[PRMUser]
  implicit val reads = Json.reads[PRMUser]
}