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
object CustomerDao {
  lazy val query = CustomerTable.query

  def searchByName(name: String)(implicit s: Session): List[CustomerEntity] = {
    query.filter(row => row.name like "%" + name + "%").list
  }

  def searchByID(id: Long)(implicit s: Session): Option[CustomerEntity] = {
    query.filter(_.id === id).firstOption
  }

  def create(customer: CustomerDTO)(implicit s: Session) = {
    //if id is O.AutoInc, of course autoIncrement
    val newC = CustomerEntity(-1, customer.name)
    query.insert(newC)
  }

  def update(customer: CustomerDTO)(implicit s: Session) = {
    val newC = CustomerEntity(customer.id, customer.name)
    query.filter(_.id === customer.id).update(newC)
  }

//  def remove(customer: CustomerEntity)(implicit s: Session) {
//    customerQuery.filter(_.id === customer.id).delete
//  }
}