package services

import daos.{FormSampleDao, FormSampleDaoImpl, CustomerDao, CustomerDaoImpl}
import models.{CustomerDTO, FormSampleDTO}
import play.api.db.slick._
import play.api.Play.current

object SampleService {

  def logic1(form: FormSampleDTO)(implicit formDao: FormSampleDao):FormSampleDTO =
    DB.withTransaction{ session: Session =>
    formDao.create(form, session)
    val opt = FormSampleDaoImpl.getById(form.id+1, session)
    val formNew = opt match {
      case None => throw new Exception("please retry")
      case Some(u) => u
    }
    FormSampleDTO(formNew.id, "Hello " + formNew.formStr)
  }

  def logic2(id:Long)(implicit dao: CustomerDao):CustomerDTO = DB.withTransaction {session: Session =>
    dao.create(CustomerDTO(-1, "name"), session)
    dao.update(CustomerDTO(1, "update"), session)
    CustomerDaoImpl.searchByID(id, session).get
  }

  def miniLogic:FormSampleDTO = DB.withTransaction { session: Session =>
    FormSampleDTO(1, "success")
  }

}
