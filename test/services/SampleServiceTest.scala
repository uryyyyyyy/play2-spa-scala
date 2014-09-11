package services

import daos.FormSampleDao
import models.FormSampleDTO
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.scalatest.mock.MockitoSugar

import org.scalatestplus.play._
import play.api.db.slick._

import scala.collection.mutable

class SampleServiceTest extends PlaySpec with MockitoSugar {

	"logic1 Test" must {
		"normal pattern1 " in {
			implicit val formDao = mock[FormSampleDao]
			when(formDao.getById(anyLong, any[Session])).thenReturn(Some(FormSampleDTO(1, "Hello ")))
			//when(formDao.create(any[FormSampleDTO], any[Session])).thenReturn(():Unit)
			val input = FormSampleDTO(1, "anyString ")
			val result = FormSampleDTO(1, "Hello ")
			SampleService.logic1(mock[Session], input) mustBe result
		}
		"illegal pattern1 " in {
			implicit val formDao = mock[FormSampleDao]
			when(formDao.getById(anyLong, any[Session])).thenReturn(None)
			//when(formDao.create(any[FormSampleDTO], any[Session])).thenReturn(():Unit)
			val input = FormSampleDTO(1, "anyString ")
			a [Exception] must be thrownBy {
				SampleService.logic1(mock[Session], input)
			}
		}
	}
}