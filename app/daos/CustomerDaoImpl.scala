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

  def searchByName(name: String, s: Session): List[CustomerEntity] = {
    query.filter(row => row.name like "%" + name + "%").list(s)
  }

  def searchByID(id: Long, s: Session): Option[CustomerEntity] = {
    query.filter(_.id === id).firstOption(s)
  }

  def create(customer: CustomerDTO, s: Session) = {
    //if id is O.AutoInc, of course autoIncrement
    val newC = CustomerEntity(-1, customer.name)
    query.insert(newC)(s)
  }

  def update(customer: CustomerDTO, s: Session) = {
    val newC = CustomerEntity(customer.id, customer.name)
    query.filter(_.id === customer.id).update(newC)(s)
  }

//  def remove(customer: CustomerEntity)(implicit s: Session) {
//    customerQuery.filter(_.id === customer.id).delete
//  }
}