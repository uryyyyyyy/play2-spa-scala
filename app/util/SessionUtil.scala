package util

import java.security.MessageDigest

import daos.UserDao
import entities.CustomerEntity
import models.{User, SessionDTO, CustomerDTO}
import play.api.cache.Cache
import play.api.db.slick._
import play.api.mvc.Request

trait SessionUtil {

  def isCorrectReq[A](request : Request[A]):Boolean

  def createSession(user: User)(s:Session): String

  def getSessionFromCache(sessionId: String):SessionDTO

}
