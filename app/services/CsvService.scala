package services

import daos.{CustomerDao, CustomerDaoImpl}
import play.api.db.slick._
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart
import util.{HashUtil, CsvUtil, S3Util}
import java.io.{PrintWriter, File}

import scala.io.Source

object CsvService {

	def importScv(postedFile: Option[FilePart[TemporaryFile]]) = {
		val csvFile = new File("tmp/" + HashUtil.hash)
		postedFile match {
			case None => "miss"
			case Some(s) => s.ref.moveTo(new File("tmp/" + HashUtil.hash))
		}
		val source = Source.fromFile(csvFile, "ms932")
		val lines = source.getLines()
		lines.foreach(CsvUtil.csvToObject)
	}

	def exportScv(session: Session, fileName: String)(implicit customerDao: CustomerDao): File = {
		val list = customerDao.searchByName("name", session)
		val csvList = list.map(CsvUtil.objectToCsv)
		val outputFile = new File("tmp/" + fileName)
		val out = new PrintWriter(outputFile, "utf8")
		csvList.foreach(out.write)
		out.close()
		outputFile
	}
}