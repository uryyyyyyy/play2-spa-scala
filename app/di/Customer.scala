package di

import daos.CustomerDaoImpl


object Customer {

  implicit def diCustomerDao(d:Dummy) = CustomerDaoImpl
}
