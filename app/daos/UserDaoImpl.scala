package daos

import models.User
import play.api.db.slick.Config.driver.simple._
import entities.{UserEntity, UserTable}

object UserDaoImpl extends UserDao{
  lazy val query = UserTable.query

  override def getById(id: String)(s: Session): Option[User] = {
    val entityOpt = query.filter(_.id === id).firstOption(s)
    entityOpt match{
      case None => None
      case Some(e) => Option(User(e.id, e.pass))
    }
  }

//  def create(user: User)(s: Session) = {
//    //if id is O.AutoInc, of course autoIncrement
//    val newUser = UserEntity(-1, user.name, user.pass)
//    query.insert(newUser)(s)
//  }
}