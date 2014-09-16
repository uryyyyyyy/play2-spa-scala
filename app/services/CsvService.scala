package services

import java.io.File

import daos.CustomerDao
import play.api.db.slick._
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart
import util.{HashUtil, CsvUtil}
import play.api.Play.current

object CsvService {

	def importCsv(postedFile: FilePart[TemporaryFile])(implicit customerDao: CustomerDao):String =
		DB.withTransaction { session:Session =>
		//use hash, (uploadedFile may be same name)
		val csvFile = new File("tmp/" + HashUtil.hash)
		//save local "tmp/" folder
		postedFile.ref.moveTo(csvFile)
		val dtos = CsvUtil.csvToObject(csvFile, "ms932")
		dtos.foreach(dto => customerDao.create(dto, session))
		return "csv update"
	}

	def exportCsv(searchName: String)(implicit customerDao: CustomerDao): File =
		DB.withTransaction { session:Session =>
		val list = customerDao.searchByName(searchName, session)
		//use hash, (uploadedFile may be same name)
		val outputFile = new File("tmp/" + HashUtil.hash)
		CsvUtil.objectToCsv(list, outputFile, "UTF-8")
	}
}