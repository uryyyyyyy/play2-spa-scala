package entities

import play.api.db.slick.Config.driver.simple._


case class UserEntity(id: String, pass: String)

class UserTable(tag: Tag) extends Table[UserEntity](tag, "users") {
  def id = column[String]("id", O.PrimaryKey, O.NotNull)
  def pass = column[String]("pass", O.NotNull)
  def * = (id, pass) <> ((UserEntity.apply _).tupled, UserEntity.unapply)
}

object UserTable {
  lazy val query = TableQuery[UserTable]
}