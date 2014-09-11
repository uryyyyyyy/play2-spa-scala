package services

import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart
import util.S3Util
import java.io.File

object S3UploadService {

	def uploadToS3(file: Option[FilePart[TemporaryFile]])(implicit s3Uploader: S3Util): String = {
		file match {
			case None => "miss"
			case Some(s) => s3Uploader.post(s)
		}
	}

	def downloadFromS3(fileName: String)(implicit s3Uploader: S3Util): File = {
		s3Uploader.download(fileName)
	}
}