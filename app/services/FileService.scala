package services

import play.api.Logger
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart
import util.S3Util
import java.io.File

object FileService {

	def uploadToS3(file: Option[FilePart[TemporaryFile]])(implicit s3Uploader: S3Util): String = {
		file match {
			case None => "miss"
			case Some(s) => s3Uploader.post(s)
		}
	}

	def downloadFromS3(fileName: String)(implicit s3Uploader: S3Util): File = {
		s3Uploader.download(fileName)
	}

	def list(implicit s3Uploader: S3Util) = {
		val strList = s3Uploader.list()
		strList.foreach(s => Logger.info(s))
	}
}