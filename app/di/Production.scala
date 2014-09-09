package di

import daos.{CustomerDaoImpl, FormSampleDaoImpl}


object Production {

  implicit val formSampleDao = FormSampleDaoImpl
  implicit val customerDao = CustomerDaoImpl
}
