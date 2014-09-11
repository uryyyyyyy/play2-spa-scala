package services

import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart
import util.S3Uploader
import java.io.File

object S3UploadService {

	def uploadToS3(file: Option[FilePart[TemporaryFile]])(implicit s3Uploader: S3Uploader): String = {
			file.map { postedFile =>
			val filename = postedFile.filename
			val contentType = postedFile.contentType
			postedFile.ref.moveTo(new File("tmp/" + filename))
			s3Uploader.post()
			return filename + contentType
		}.getOrElse {
			return "miss"
		}
	}
}