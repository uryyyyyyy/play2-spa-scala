package services

import play.api.Logger
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart
import util.S3Util
import java.io.File

object FileService {

	def uploadToS3(file: FilePart[TemporaryFile])(implicit s3Uploader: S3Util): String = {
		s3Uploader.post(file)
	}

	def downloadFromS3(fileName: String)(implicit s3Uploader: S3Util): File = {
		s3Uploader.download(fileName)
	}

	def list(implicit s3Uploader: S3Util) = {
		val strList = s3Uploader.list()
		strList.foreach(s => Logger.info(s))
	}
}