package di

import daos.CustomerDao


object Customer {

  implicit def diCustomerDao(d:Dummy) = CustomerDao
}
