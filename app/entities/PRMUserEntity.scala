package entities

import play.api.db.slick.Config.driver.simple._


case class PRMUserEntity(id: Long, name: String, pass: String)

class PRMUserTable(tag: Tag) extends Table[PRMUserEntity](tag, "prm_user") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.NotNull)
  def pass = column[String]("pass", O.NotNull)
  def * = (id, name, pass) <> ((PRMUserEntity.apply _).tupled, PRMUserEntity.unapply)
}

object PRMUserTable {
  lazy val query = TableQuery[PRMUserTable]
}