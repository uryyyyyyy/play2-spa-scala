package daos

import entities.{FormSampleEntity, CustomerEntity}
import models.{FormSampleDTO, CustomerDTO}
import play.api.db.slick.Session

trait FormSampleDao {

  def getById(id: Long, s: Session): Option[FormSampleDTO]

  def create(form: FormSampleDTO, s: Session)

  def update(form: FormSampleDTO, s: Session)

}
