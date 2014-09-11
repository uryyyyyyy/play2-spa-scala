package services

import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart
import util.S3Uploader
import java.io.File

object S3UploadService {

	def uploadToS3(file: Option[FilePart[TemporaryFile]])(implicit s3Uploader: S3Uploader): String = {
		file.map { postedFile =>
			s3Uploader.post(postedFile)
		}.getOrElse {
			"miss"
		}
	}

	def downloadFromS3(fileName: String)(implicit s3Uploader: S3Uploader): File = {
		s3Uploader.download(fileName)
	}
}