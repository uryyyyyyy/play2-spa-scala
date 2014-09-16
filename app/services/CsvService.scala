package services

import daos.CustomerDao
import play.api.db.slick._
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart
import util.{HashUtil, CsvUtil}
import java.io._

object CsvService {

	def importCsv(postedFile: Option[FilePart[TemporaryFile]], s:Session)(implicit customerDao: CustomerDao) = {
		val csvFile = new File("tmp/" + HashUtil.hash)
		postedFile match {
			case None => "miss"
			case Some(o) => o.ref.moveTo(csvFile)
		}
		val dtos = CsvUtil.csvToObject(csvFile, "ms932")
		dtos.foreach(dto => customerDao.create(dto, s))
	}

	def exportCsv(session: Session, fileName: String)(implicit customerDao: CustomerDao): File = {
		val list = customerDao.searchByName("name", session)
		val outputFile = new File("tmp/" + fileName)
		CsvUtil.objectToCsv(list, outputFile, "UTF-8")
	}
}