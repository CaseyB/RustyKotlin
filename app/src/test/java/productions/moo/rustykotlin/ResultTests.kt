package productions.moo.rustykotlin

import kotlin.reflect.typeOf
import org.junit.Test
import org.junit.Assert.*

class ResultTests {
  @Test
  fun result_canBeOk() {
    val result: Result<String> = Ok("Ok")
    when (result) {
      is Ok<*> -> assertEquals(result.value, "Ok")
      is Error<*> -> fail("Result should be Ok")
    }

    assertTrue(result.isOk)
    assertFalse(result.isError)
  }

  @Test
  fun result_canBeError() {
    val result: Result<String> = Error(Exception("Some Name"))
    when (result) {
      is Ok<*> -> fail("Result should be an error")
      is Error<*> -> {
        assertEquals(result.error.message, "Some Name")
      }
    }

    assertTrue(result.isError)
    assertFalse(result.isOk)
  }

  @Test
  fun result_isOk_unwrap() {
    val result: Result<String> = Ok("Ok")
    assertEquals(result.unwrap(), "Ok")
  }

  @Test(expected = Exception::class)
  fun result_isError_unwrap() {
    val result: Result<String> = Error(Exception("Some Name"))
    result.unwrap()
  }

  @Test(expected = NullPointerException::class)
  fun result_isOk_unwrapError() {
    val result: Result<String> = Ok("Ok")
    result.unwrapError()
  }

  @Test
  fun result_isError_unwrapError() {
    val result: Result<String> = Error(Exception("Message"))
    val actual = result.unwrapError()
    assert(actual::class.java == Exception::class.java)
    assertEquals(actual.message, "Message")
  }

  @Test
  fun result_isOk_unwrapOr() {
    val result: Result<String> = Ok("Ok")
    assertEquals(result.unwrapOr("Error"), "Ok")
  }

  @Test
  fun result_isError_unwrapOr() {
    val result: Result<String> = Error(Exception("Some Name"))
    assertEquals(result.unwrapOr("Ok"), "Ok")
  }

  @Test
  fun result_isOk_unwrapOrElse() {
    val result: Result<String> = Ok("Ok")
    assertEquals(result.unwrapOrElse{"Error"}, "Ok")
  }

  @Test
  fun result_isError_unwrapOrEse() {
    val result: Result<String> = Error(Exception("Some Name"))
    assertEquals(result.unwrapOrElse{"Ok"}, "Ok")
  }

  @Test
  fun result_isOk_errorOrNull() {
    val result: Result<String> = Ok("Ok")
    assertNull(result.errorOrNull())
  }

  @Test
  fun result_isError_errorOrNull() {
    val result: Result<String> = Error(Exception("Some Name"))
    assertNotNull(result.errorOrNull())
  }

  @Test
  fun result_isOk_toString() {
    val result: Result<String> = Ok("Message")
    assertEquals(result.toString(), "Ok: Message")
  }

  @Test
  fun result_isError_toString() {
    val result: Result<String> = Error(Exception("Message"))
    assertEquals(result.toString(), "Error: Message")
  }

  @Test
  fun result_isOk_expect() {
    val result: Result<String> = Ok("Message")
    assertEquals(result.expect("Error Message"), "Message")
  }

  @Test(expected = NullPointerException::class)
  fun result_isError_expect() {
    val result: Result<String> = Error(Exception("Message"))
    result.expect("Error Message")
  }

  @Test(expected = NullPointerException::class)
  fun result_isOk_expectError() {
    val result: Result<String> = Ok("Message")
    result.expectError("Error Message")
  }

  @Test
  fun result_isError_expectError() {
    val result: Result<String> = Error(Exception("Message"))
    val actual = result.expectError("Error Message")
    assert(actual::class.java == Exception::class.java)
    assertEquals(actual.message, "Message")
  }

  @Test
  fun ok_hashCode() {
    assertEquals(Ok("Message").hashCode(), "Message".hashCode())
  }

  @Test
  fun error_hashCode() {
    val exception = Exception("Message")
    assertEquals(Error<String>(exception).hashCode(), exception.hashCode())
  }
}