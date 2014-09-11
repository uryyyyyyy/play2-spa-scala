package di

import daos.{UserDaoImpl, CustomerDaoImpl, FormSampleDaoImpl}
import util.{S3UtilImpl, SessionUtilImpl}


object Production {

	implicit val formSampleDao = FormSampleDaoImpl
	implicit val customerDao = CustomerDaoImpl
	implicit val userDao = UserDaoImpl

	implicit val sessionUtil = SessionUtilImpl
	implicit val s3Uploader = S3UtilImpl
}