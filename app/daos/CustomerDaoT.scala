package daos

import entities.CustomerEntity
import models.CustomerDTO
import play.api.db.slick.Session

trait CustomerDaoT {

  def searchByName(name: String)(implicit s: Session): List[CustomerEntity]

  def searchByID(id: Long)(implicit s: Session): Option[CustomerEntity]

  def create(customer: CustomerDTO)(implicit s: Session)

  def update(customer: CustomerDTO)(implicit s: Session)

}
