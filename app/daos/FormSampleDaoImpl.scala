package daos

import models.FormSampleDTO
import play.api.db.slick.Config.driver.simple._
import entities.{FormSampleEntity, FormSampleTable}

object FormSampleDaoImpl {
  lazy val query = FormSampleTable.query

  def getById(id: Long, s: Session): Option[FormSampleDTO] = {
    val opt = query.filter(_.id === id).firstOption(s)
    opt match {
      case None => None
      case Some(u) => Option(FormSampleDTO(u.id, u.formStr))
    }
  }

  def create(form: FormSampleDTO, s: Session) = {
    //if id is O.AutoInc, of course autoIncrement
    val newF = FormSampleEntity(form.id, form.formStr)
    query.insert(newF)(s)
  }

  def update(form: FormSampleDTO, s: Session) = {
    query.filter(_.id === form.id).update(FormSampleEntity(form.id, form.formStr))(s)
  }
}