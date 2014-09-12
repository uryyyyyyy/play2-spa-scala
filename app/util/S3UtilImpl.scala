package util

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{S3ObjectSummary, ListObjectsRequest, GetObjectRequest, PutObjectRequest}
import com.amazonaws.services.s3.transfer.{Download, TransferManager}
import java.io.File
import play.api.Play.current
import play.api.{Logger, Play}
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart

import scala.collection.JavaConverters._
import scala.collection.mutable


object S3UtilImpl extends S3Util {
	//`ProfileCredentialsProvider` check your local credential file(~/.aws/credential)
	val credential = new ProfileCredentialsProvider
	val s3Client = new AmazonS3Client(new ProfileCredentialsProvider)
	val s3BucketName = Play.application.configuration.getString("aws.s3.bucketName").get

	override def post(postedFile: FilePart[TemporaryFile]): String = {
		//val contentType = postedFile.contentType
		val file = new File("tmp/upload/" + HashUtil.hash)
		postedFile.ref.moveTo(file)
		val s3FilePath = "files/" + postedFile.filename
		val upReq = new PutObjectRequest(s3BucketName, s3FilePath, file)
		s3Client.putObject(upReq)
		postedFile.filename
	}

	override def download(fileName: String): File = {
		val tm = new TransferManager(credential)
		val downloadingFile = new File("./tmp/download/" + HashUtil.hash)
		val s3FilePath = "files/" + fileName
		val req = new GetObjectRequest(s3BucketName, s3FilePath)
		try {
			downloading(tm.download(req, downloadingFile))
			downloadingFile
		} finally {
			tm.shutdownNow()
		}
	}

	private def downloading(download: Download) {
		while (!download.isDone) {
			Logger.info(s"progress ${download.getProgress.getPercentTransferred}%")
			Thread.sleep(100)
		}
		download.waitForCompletion()
	}

	override def list() = {
		val s3FilePath = "files/"
		val listReq = new ListObjectsRequest()
		listReq.setPrefix(s3FilePath)
		listReq.setBucketName(s3BucketName)

		val objectListing = s3Client.listObjects(listReq)
		val summaryList: mutable.Buffer[S3ObjectSummary] = objectListing.getObjectSummaries.asScala

		summaryList.foreach(s => Logger.info(s.getKey + " : " + s.getSize + "byte"))
	}
}