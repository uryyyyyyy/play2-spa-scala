package util

import java.io.File

trait S3Util {

  def post(file : File):String

  def download(fileName: String): File

  def list():List[String]
}