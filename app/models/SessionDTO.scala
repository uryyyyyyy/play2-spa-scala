package models

import play.api.libs.json.Json

case class SessionDTO(name: String, sessionId: String)

object SessionDTO {
	//these implicit serve convert(Json <-> ScalaObject)
	implicit val writes = Json.writes[SessionDTO]
	implicit val reads = Json.reads[SessionDTO]
}