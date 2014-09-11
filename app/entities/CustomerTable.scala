package entities

/**
 * 1 import package related in Slick
 */
import play.api.db.slick.Config.driver.simple._

/**
 * 2 DTO definition
 * models.Customer
 */
case class CustomerEntity(id: Long, name: String)

/**
 * 3 DB table definition
 */
class CustomerTable(tag: Tag) extends Table[CustomerEntity](tag, "customers") {
	def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
	def name = column[String]("name", O.NotNull)

	//Customer.tupled will error when companion object is in class,
	//so have to avoid like this (Customer.apply _).tupled
	override def * = (id, name) <>((CustomerEntity.apply _).tupled, CustomerEntity.unapply)
}

object CustomerTable {
	lazy val query = TableQuery[CustomerTable]
}

/**
 * 4 DAO definition
 * (daos.CustomerDao)
 */