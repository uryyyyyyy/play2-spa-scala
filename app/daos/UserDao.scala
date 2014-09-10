package daos

import models.User
import play.api.db.slick.Config.driver.simple._
import entities.{UserEntity, UserTable}

object UserDao {
  lazy val query = UserTable.query

  def getByName(name: String)(implicit s: Session): Option[User] = {
    val entityOpt = query.filter(_.name === name).firstOption
    entityOpt match{
      case None => None
      case Some(e) => Option(User(e.name, e.pass))
    }
  }

  def create(user: User)(implicit s: Session) = {
    //if id is O.AutoInc, of course autoIncrement
    val newUser = UserEntity(-1, user.name, user.pass)
    query.insert(newUser)
  }
//
//  def update(form: PRMUser)(implicit s: Session) = {
//    query.filter(_.id === form.id).update(FormSampleEntity(form.id, form.formStr))
//  }
}