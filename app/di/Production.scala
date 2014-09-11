package di

import daos.{CustomerDaoImpl, FormSampleDaoImpl}
import util.{S3Uploader, SessionUtilImpl}


object Production {

  implicit val formSampleDao = FormSampleDaoImpl
  implicit val customerDao = CustomerDaoImpl

  implicit val sessionUtil = SessionUtilImpl
  implicit val s3Uploader = S3Uploader
}
