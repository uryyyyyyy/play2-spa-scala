package util

import java.io.File

import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart

trait S3Uploader {

  def post(file : FilePart[TemporaryFile]):String

  def download(fileName: String): File

  def list()
}