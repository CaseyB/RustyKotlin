package productions.moo.rustykotlin

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
}