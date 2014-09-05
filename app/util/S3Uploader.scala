package util

import com.amazonaws.auth._
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model._
import java.io.File

object S3Uploader extends App {

  //アクセスキー
  val accessKey = "アクセスキー"
  //シークレットキー
  val secretKey = "シークレットキー"


  def put(localFile:File) = {
    val credentials = new BasicAWSCredentials(accessKey,secretKey)
    val s3Client = new AmazonS3Client(credentials)
    val s3BucketName = "バケット名"
    val s3FilePath = "S3のアップロード先のパス"
    val upReq = new PutObjectRequest(s3BucketName, s3FilePath, localFile)
    s3Client.putObject(upReq)
  }

  def getList() = {
    val credentials = new BasicAWSCredentials(accessKey,secretKey)
    val s3Client = new AmazonS3Client(credentials)

    val s3BucketName = "バケット名"
    val s3FilePath = "S3のディレクトリ"

    //リスティングで多すぎる場合、マーカーを保持しておかないといけない。
    var preNextMarker = ""
    var nextMarker = ""
    var flg = false

    while(!flg){

      preNextMarker = nextMarker

      val listReq = new ListObjectsRequest()
      listReq.setPrefix(s3FilePath)
      listReq.setBucketName(s3BucketName)

      listReq.setMarker(nextMarker)

      val objectListing = s3Client.listObjects(listReq)
      val summary = objectListing.getObjectSummaries

      //Listが返ってくるので、ぐるぐるまわす。
      //ScalaのListではないので、注意
      (0 to summary.size - 1).foreach{s3Object =>
        val obj = summary.get(s3Object)
        //煮るなり焼くなり
      }
      //println("next marker : " + nextMarker)

      nextMarker = objectListing.getNextMarker

      //もう無ければマーカーはnullです。
      if(nextMarker == null) flg = true
    }
  }
}