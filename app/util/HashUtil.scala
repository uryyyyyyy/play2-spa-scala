package util

import java.security.MessageDigest

object HashUtil {

	def hash(s: String): String = {
		val md = MessageDigest.getInstance("SHA-1")
		md.update(s.getBytes)
		md.digest.foldLeft("") { (s, b) => s + "%02x".format(if (b < 0) b + 256 else b)}
	}

	def hash: String = {
		java.util.UUID.randomUUID.toString
	}

}
