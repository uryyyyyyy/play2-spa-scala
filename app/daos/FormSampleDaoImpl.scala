package daos

import entities.{FormSampleEntity, FormSampleTable}
import models.FormSampleDTO
import play.api.db.slick
import play.api.db.slick.Config.driver.simple._

object FormSampleDaoImpl extends FormSampleDao {
	lazy val query = FormSampleTable.query

	override def getById(id: Long, s: Session): Option[FormSampleDTO] = {
		val opt = query.filter(_.id === id).firstOption(s)
		opt match {
			case None => None
			case Some(u) => Option(FormSampleDTO(u.id, u.formStr))
		}
	}

	override def create(form: FormSampleDTO, s: Session) = {
		//if id is O.AutoInc, of course autoIncrement
		val newF = FormSampleEntity(form.id, form.formStr)
		query.insert(newF)(s)
	}

	override def update(form: FormSampleDTO, s: Session) = {
		query.filter(_.id === form.id).update(FormSampleEntity(form.id, form.formStr))(s)
	}

	override def getByStr(str: String, s: slick.Session): List[FormSampleDTO] = {
		val list = query.filter(row => row.formStr like "%" + str + "%").list(s)
		list.map(entity => FormSampleDTO(entity.id, entity.formStr))
	}
}