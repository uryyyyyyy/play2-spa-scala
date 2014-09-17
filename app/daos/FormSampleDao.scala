package daos

import models.FormSampleDTO
import play.api.db.slick.Session

trait FormSampleDao {

	def getById(id: Long, s: Session): Option[FormSampleDTO]

	def getByStr(str: String, s: Session): List[FormSampleDTO]

	def create(form: FormSampleDTO, s: Session)

	def update(form: FormSampleDTO, s: Session)

}