package services

import daos.{CustomerDaoT, CustomerDao, FormSampleDao}
import models.{CustomerDTO, FormSampleDTO}
import play.api.db.slick._
import play.api.Play.current

object SampleService {

  def logic1(form: FormSampleDTO):FormSampleDTO = DB.withSession{implicit  session =>
    FormSampleDao.create(form)
    val opt = FormSampleDao.getById(form.id+1)
    val formNew = opt match {
      case None => throw new Exception("please retry")
      case Some(u) => u
    }
    FormSampleDTO(formNew.id, "Hello " + formNew.formStr)
  }

  def logic2(id:Long, dao: CustomerDaoT):CustomerDTO = DB.withSession {implicit session =>
    dao.create(CustomerDTO(-1, "name"))
    dao.update(CustomerDTO(1, "update"))
    val customer = CustomerDao.searchByID(id).get
    CustomerDTO(customer.id, customer.name)
  }

  def miniLogic():FormSampleDTO = DB.withSession{implicit session =>
    FormSampleDTO(1, "success")
  }

}
