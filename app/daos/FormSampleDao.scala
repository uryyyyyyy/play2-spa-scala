package daos

import models.FormSampleDTO
import play.api.db.slick.Config.driver.simple._
import entities.{FormSampleEntity, FormSampleTable}

object FormSampleDao {
  lazy val query = FormSampleTable.query

  def getById(id: Long)(implicit s: Session): Option[FormSampleDTO] = {
    val opt = query.filter(_.id === id).firstOption
    opt match {
      case None => None
      case Some(u) => Option(FormSampleDTO(u.id, u.formStr))
    }
  }

  def create(form: FormSampleDTO)(implicit s: Session) = {
    //if id is O.AutoInc, of course autoIncrement
    val newF = FormSampleEntity(form.id, form.formStr)
    query.insert(newF)
  }

  def update(form: FormSampleDTO)(implicit s: Session) = {
    query.filter(_.id === form.id).update(FormSampleEntity(form.id, form.formStr))
  }
}