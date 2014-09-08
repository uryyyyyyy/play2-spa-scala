package services

import daos.{CustomerDao, CustomerDaoImpl, FormSampleDaoImpl}
import models.{CustomerDTO, FormSampleDTO}
import play.api.db.slick._
import play.api.Play.current

object SampleService {

  def logic1(form: FormSampleDTO):FormSampleDTO = DB.withTransaction{ session: Session =>
    FormSampleDaoImpl.create(form, session)
    val opt = FormSampleDaoImpl.getById(form.id+1, session)
    val formNew = opt match {
      case None => throw new Exception("please retry")
      case Some(u) => u
    }
    FormSampleDTO(formNew.id, "Hello " + formNew.formStr)
  }

  def logic2(id:Long, dao: CustomerDao):CustomerDTO = DB.withTransaction {session: Session =>
    dao.create(CustomerDTO(-1, "name"), session)
    dao.update(CustomerDTO(1, "update"), session)
    val customer = CustomerDaoImpl.searchByID(id, session).get
    CustomerDTO(customer.id, customer.name)
  }

  def miniLogic:FormSampleDTO = DB("default").withTransaction { session: Session =>
    FormSampleDTO(1, "success")
  }

}
