package daos

import entities.CustomerEntity
import models.CustomerDTO
import play.api.db.slick.Session

trait CustomerDao {

  def searchByName(name: String, s: Session): List[CustomerEntity]

  def searchByID(id: Long, s: Session): Option[CustomerEntity]

  def create(customer: CustomerDTO, s: Session)

  def update(customer: CustomerDTO, s: Session)

}
