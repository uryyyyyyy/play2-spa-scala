package daos

import models.PRMUser
import play.api.db.slick.Config.driver.simple._
import entities.{PRMUserEntity, PRMUserTable}

object UserDao {
  lazy val query = PRMUserTable.query

  def getByName(name: String)(implicit s: Session): Option[PRMUser] = {
    val entityOpt = query.filter(_.name === name).firstOption
    entityOpt match{
      case None => None
      case Some(e) => Option(PRMUser(e.name, e.pass))
    }
  }

  def create(user: PRMUser)(implicit s: Session) = {
    //if id is O.AutoInc, of course autoIncrement
    val newUser = PRMUserEntity(-1, user.name, user.pass)
    query.insert(newUser)
  }
//
//  def update(form: PRMUser)(implicit s: Session) = {
//    query.filter(_.id === form.id).update(FormSampleEntity(form.id, form.formStr))
//  }
}