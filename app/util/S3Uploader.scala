package util

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{ListObjectsRequest, GetObjectRequest, PutObjectRequest}
import com.amazonaws.services.s3.transfer.TransferManager
import java.io.File
import play.api.Play.current
import play.api.Play


object S3Uploader {
  val credential = new ProfileCredentialsProvider("sample")
  val s3Client = new AmazonS3Client(new ProfileCredentialsProvider("sample"))
  val bucketName = Play.application.configuration.getString("aws.s3.bucketName").get

  def post = {
    print("user認証クリア")
    val localFile = new File("./build.sbt")
    val s3FilePath = "build.sbt"
    val upReq = new PutObjectRequest(bucketName, s3FilePath, localFile)
    s3Client.putObject(upReq)
  }

  def download() = {

    //オブジェクトのパス
    val s3ObjectPath = "test.txt"
    //ダウンロード先のパス（ローカル）
    val downloadFile = new File("./test/test.txt")

    //ダウンロード中のパス
    val downloadingFile = new File("./test/test.txt" + ".tmp")

    val req = new GetObjectRequest(bucketName, s3ObjectPath)
    val tm = new TransferManager(credential)
    val s3Obj = new AmazonS3Client().getObject(req)

    try {

      val totalWork =  s3Obj.getObjectMetadata.getContentLength
      val download = tm.download(req, downloadingFile)

      var lastTransferred = 0L

      while (!download.isDone) {
        val transferred = download.getProgress.getBytesTransferred
        lastTransferred = transferred

        println(s"progress ${download.getProgress.getPercentTransferred}%")

        Thread.sleep(100)
      }
      download.waitForCompletion()

    } finally {
      try {
        tm.shutdownNow()
      }

      //ファイルをリネーム
      downloadingFile.renameTo(downloadFile)

    }
  }

  def list() = {
    val s3FilePath = ""

    //リスティングで多すぎる場合、マーカーを保持しておかないといけない。
    var preNextMarker = ""
    var nextMarker = ""
    var flg = false

    while(!flg){

      preNextMarker = nextMarker

      val listReq = new ListObjectsRequest()
      listReq.setPrefix(s3FilePath)
      listReq.setBucketName(bucketName)

      listReq.setMarker(nextMarker)

      val objectListing = s3Client.listObjects(listReq)
      val summary = objectListing.getObjectSummaries

      //Listが返ってくるので、ぐるぐるまわす。
      //ScalaのListではないので、注意
      (0 to summary.size - 1).foreach{s3Object =>
        val obj = summary.get(s3Object)
        //煮るなり焼くなり
        println(obj.getKey + " : " + obj.getSize + "byte")
      }
      println("next marker : " + nextMarker)

      nextMarker = objectListing.getNextMarker

      //もう無ければマーカーはnullです。
      if(nextMarker == null) flg = true
    }
  }
}