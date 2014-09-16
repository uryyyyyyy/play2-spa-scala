package util

import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart
import play.api.mvc.{AnyContent, Request}

object PlayFileUploadUtil {
	def checkFileExist(request: Request[AnyContent]):Option[FilePart[TemporaryFile]] = {
		request.body.asMultipartFormData match {
			case None => None
			case Some(mpfData) =>
				mpfData.file("file") match {
					case None => None
					case Some(file) => Some(file)
				}
		}
	}
}
