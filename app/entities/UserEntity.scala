package entities

import play.api.db.slick.Config.driver.simple._


case class UserEntity(id: Long, name: String, pass: String)

class UserTable(tag: Tag) extends Table[UserEntity](tag, "user") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("username", O.NotNull)
  def pass = column[String]("pass", O.NotNull)
  def * = (id, name, pass) <> ((UserEntity.apply _).tupled, UserEntity.unapply)
}

object UserTable {
  lazy val query = TableQuery[UserTable]
}