package controllers

import org.mockito.Mockito._
import org.mockito.Matchers._
import org.scalatest.mock.MockitoSugar

import collection.mutable.Stack
import org.scalatestplus.play._

class SampleControllerTest extends PlaySpec with MockitoSugar {

    "A Stack" must {
        "pop values in last-in-first-out order" in {
            val stack = new Stack[Int]
            stack.push(1)
            stack.push(2)
            stack.pop() mustBe 2
            stack.pop() mustBe 1
        }
        "throw NoSuchElementException if an empty stack is popped" in {
            val emptyStack = new Stack[Int]
            a [NoSuchElementException] must be thrownBy {
                emptyStack.pop()
            }
        }
    }

    "Mock Sample" must{
      "hoge" in {
        val hoge = mock[SampleController]
        when(hoge.getFuga(anyInt())).thenReturn(-1) // どんな数が来ても -1 を返す
        when(hoge.getFuga(1)).thenReturn(100) // ただし、1の時は100を返す
        when(hoge.getFuga(2)).thenReturn(null) // ただし、2の時はnullを返す
      }
    }
}