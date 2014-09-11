package entities

import play.api.db.slick.Config.driver.simple._

case class FormSampleEntity(id: Long, formStr: String)

class FormSampleTable(tag: Tag) extends Table[FormSampleEntity](tag, "form_sample") {
	def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
	def formStr = column[String]("form_str", O.NotNull)
	override def * = (id, formStr) <>((FormSampleEntity.apply _).tupled, FormSampleEntity.unapply)
}

object FormSampleTable {
	lazy val query = TableQuery[FormSampleTable]
}