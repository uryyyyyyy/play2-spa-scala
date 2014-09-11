package daos

import models.CustomerDTO
import play.api.db.slick.Session

trait CustomerDao {

  def searchByName(name: String, s: Session): List[CustomerDTO]

  def searchByID(id: Long, s: Session): Option[CustomerDTO]

  def create(customer: CustomerDTO, s: Session)

  def update(customer: CustomerDTO, s: Session)

}
