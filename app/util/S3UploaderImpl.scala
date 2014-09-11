package util

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{ListObjectsRequest, GetObjectRequest, PutObjectRequest}
import com.amazonaws.services.s3.transfer.TransferManager
import java.io.File
import play.api.Play.current
import play.api.Play
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart


object S3UploaderImpl extends S3Uploader {
  val credential = new ProfileCredentialsProvider
  val s3Client = new AmazonS3Client(new ProfileCredentialsProvider)
  val s3BucketName = Play.application.configuration.getString("aws.s3.bucketName").get

	override def post(postedFile : FilePart[TemporaryFile]):String = {
		//val contentType = postedFile.contentType
		val file = new File("tmp/upload/" + HashUtil.hash)
		postedFile.ref.moveTo(file)
		val upReq = new PutObjectRequest(s3BucketName, postedFile.filename, file)
		s3Client.putObject(upReq)
		postedFile.filename
	}

	override def download(fileName: String): File = {

		val req = new GetObjectRequest(s3BucketName, fileName)
		val tm = new TransferManager(credential)
		val s3Obj = new AmazonS3Client().getObject(req)
		//ダウンロード中のパス
		val downloadingFile = new File("./tmp/download/" + HashUtil.hash + ".download")

		try {

			val totalWork = s3Obj.getObjectMetadata.getContentLength
			val download = tm.download(req, downloadingFile)

			var lastTransferred = 0L

			while (!download.isDone) {
				val transferred = download.getProgress.getBytesTransferred
				lastTransferred = transferred

				println(s"progress ${download.getProgress.getPercentTransferred}%")

				Thread.sleep(100)
			}
			download.waitForCompletion()
			download
			//ファイルをリネーム
			downloadingFile.renameTo(new File("./tmp/download/" + fileName))
			downloadingFile
		} finally {
			try {
				tm.shutdownNow()
			}
		}
	}

	override def list() = {
		val s3FilePath = ""

		//リスティングで多すぎる場合、マーカーを保持しておかないといけない。
		var preNextMarker = ""
		var nextMarker = ""
		var flg = false

		while (!flg) {

			preNextMarker = nextMarker

			val listReq = new ListObjectsRequest()
			listReq.setPrefix(s3FilePath)
			listReq.setBucketName(s3BucketName)

			listReq.setMarker(nextMarker)

			val objectListing = s3Client.listObjects(listReq)
			val summary = objectListing.getObjectSummaries

			//Listが返ってくるので、ぐるぐるまわす。
			//ScalaのListではないので、注意
			(0 to summary.size - 1).foreach { s3Object =>
				val obj = summary.get(s3Object)
				//煮るなり焼くなり
				println(obj.getKey + " : " + obj.getSize + "byte")
			}
			println("next marker : " + nextMarker)

			nextMarker = objectListing.getNextMarker

			//もう無ければマーカーはnullです。
			if (nextMarker == null) flg = true
		}
	}
}