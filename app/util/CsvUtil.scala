package util

import models.CustomerDTO

object CsvUtil {

	def csvToObject(line: String): CustomerDTO = {
		val list = line split ','
		CustomerDTO(list(1).toInt, list(2))
	}

	def objectToCsv(obj: CustomerDTO): String = {
		obj.id + "," + obj.name + "\n"
	}

	// 前後のダブルクォーテーションを除去
	def dropQuote(str : String) : String = {
		str drop 1 dropRight 1
	}
}
