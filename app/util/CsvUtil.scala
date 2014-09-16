package util

import java.io.{PrintWriter, InputStreamReader, FileInputStream, File}

import au.com.bytecode.opencsv.{CSVWriter, CSVReader}
import models.CustomerDTO
import play.api.Logger

object CsvUtil {
	def csvToObject(file: File, charCode: String):Iterator[CustomerDTO] = {
		val input = new FileInputStream(file)
		val reader = new CSVReader(new InputStreamReader(input, charCode))
		Iterator.continually(reader.readNext).takeWhile(_ != null).drop(1).map {
			line => csvToObject(line)
		}
	}

	def objectToCsv(list: List[CustomerDTO], file:File, charCode: String): File = {
		val writer = new CSVWriter(new PrintWriter(file, charCode))
		list.map(objectToCsv).foreach(writer.writeNext)
		writer.flush()
		file
	}

	private def csvToObject(line: Array[String]): CustomerDTO = {
		Logger.info(line(0) + " " +  line(6))
		CustomerDTO(line(0).toInt, line(6))
	}

	private def objectToCsv(dto : CustomerDTO):Array[String] = {
		Array(dto.id.toString, dto.name)
	}
}
