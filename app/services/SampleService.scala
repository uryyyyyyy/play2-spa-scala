package services

import daos.{FormSampleDao, CustomerDao}
import models.{CustomerDTO, FormSampleDTO}
import play.api.db.slick._
import play.api.Play.current

object SampleService {

	def logic1(session: Session, form: FormSampleDTO)(implicit formDao: FormSampleDao): FormSampleDTO = {
		formDao.create(form, session)
		val opt = formDao.getById(form.id + 1, session)
		val formNew = opt match {
			case None => throw new Exception("please retry")
			case Some(u) => u
		}
		FormSampleDTO(formNew.id, formNew.formStr)
	}

	def logic2(id: Long)(implicit dao: CustomerDao): CustomerDTO = DB.withTransaction { session: Session =>
		dao.create(CustomerDTO(-1, "name"), session)
		dao.update(CustomerDTO(1, "update"), session)
		dao.searchByID(id, session).get
	}

	def miniLogic: FormSampleDTO = DB.withTransaction { session: Session =>
		FormSampleDTO(1, "success")
	}

}