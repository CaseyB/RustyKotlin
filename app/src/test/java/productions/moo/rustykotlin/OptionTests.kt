package productions.moo.rustykotlin

import java.lang.NullPointerException
import org.junit.Test
import org.junit.Assert.*

class OptionTests {
  @Test
  fun option_canBeSome() {
    val option: Option<String> = Some("Hello World")
    when (option) {
      is Some<*> -> assertEquals(option.value, "Hello World")
      is None<*> -> fail("Option should be Some")
    }

    assertTrue(option.isSome)
    assertFalse(option.isNone)
  }

  @Test
  fun option_canBeNone() {
    val option: Option<String> = None()
    when (option) {
      is Some<*> -> fail("Option should be None")
      is None<*> -> {}
    }

    assertTrue(option.isNone)
    assertFalse(option.isSome)
  }

  @Test
  fun option_isSome_expect() {
    val option: Option<String> = Some("Message")
    assertEquals(option.expect("Unneeded error message"), "Message")
  }

  @Test(expected = Exception::class)
  fun option_isNone_expect() {
    val option: Option<String> = None()
    option.expect("Error Message")
  }

  @Test
  fun option_isSome_unwrap() {
    val option: Option<String> = Some("Message")
    assertEquals(option.unwrap(), "Message")
  }

  @Test(expected = NullPointerException::class)
  fun option_isNone_unwrap() {
    val option: Option<String> = None()
    option.unwrap()
  }

  @Test
  fun option_isSome_unwrapOr() {
    val option: Option<String> = Some("Message")
    assertEquals(option.unwrapOr("Not This"), "Message")
  }

  @Test
  fun option_isNone_unwrapOr() {
    val option: Option<String> = None()
    assertEquals(option.unwrapOr("Message"), "Message")
  }

  @Test
  fun option_isSome_unwrapOrElse() {
    val option: Option<String> = Some("Message")
    assertEquals(option.unwrapOrElse { "Not This" }, "Message")
  }

  @Test
  fun option_isNone_unwrapOrElse() {
    val option: Option<String> = None()
    assertEquals(option.unwrapOrElse { "Message" }, "Message")
  }

  @Test
  fun option_isSome_okOr() {
    val option: Option<String> = Some("Message")
    assertEquals(option.okOr(NullPointerException("Not this")), Ok("Message"))
  }

  @Test(expected = NullPointerException::class)
  fun option_isNone_okOr() {
    val option: Option<String> = None()
    option.okOr(NullPointerException("Message"))
  }

  @Test
  fun option_isSome_okOrElse() {
    val option: Option<String> = Some("Message")
    assertEquals(option.okOrElse { NullPointerException("Not this") }, "Message")
  }

  @Test(expected = NullPointerException::class)
  fun option_isNone_okOrElse() {
    val option: Option<String> = None()
    option.okOrElse { NullPointerException("Message") }
  }

  @Test
  fun option_isSome_toString() {
    val option: Option<String> = Some("Message")
    assertEquals(option.toString(), "Some: Message")
  }

  @Test
  fun option_isNone_toString() {
    val option: Option<String> = None()
    assertEquals(option.toString(), "None")
  }
}