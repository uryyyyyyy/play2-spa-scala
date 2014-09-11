package daos

import models.User
import play.api.db.slick.Session

trait UserDao {

  def getById(id: String)(s: Session): Option[User]

  //def create(user: User)(s: Session)

  //def update(user: User)(s: Session)

}
