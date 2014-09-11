package daos

import entities.{CustomerEntity, CustomerTable}
import models.CustomerDTO

/**
 * 1 import package related in Slick
 */
import play.api.db.slick.Config.driver.simple._

/**
 * 4 DAO definition
 */
object CustomerDaoImpl extends CustomerDao{
  lazy val query = CustomerTable.query

  override def searchByName(name: String, s: Session): List[CustomerDTO] = {
    val list = query.filter(row => row.name like "%" + name + "%").list(s)
    list.map(entity => CustomerDTO(entity.id, entity.name))
  }

  override def searchByID(id: Long, s: Session): Option[CustomerDTO] = {
    val opt = query.filter(_.id === id).firstOption(s)
    opt match {
      case None => None
      case Some(entity) => Option(CustomerDTO(entity.id, entity.name))
    }
  }

  override def create(customer: CustomerDTO, s: Session) = {
    //if id is O.AutoInc, of course autoIncrement
    val newC = CustomerEntity(-1, customer.name)
    query.insert(newC)(s)
  }

  override def update(customer: CustomerDTO, s: Session) = {
    val newC = CustomerEntity(customer.id, customer.name)
    query.filter(_.id === customer.id).update(newC)(s)
  }

//  def remove(customer: CustomerEntity)(implicit s: Session) {
//    customerQuery.filter(_.id === customer.id).delete
//  }
}