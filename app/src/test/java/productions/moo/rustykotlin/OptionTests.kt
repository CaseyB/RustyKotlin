package productions.moo.rustykotlin

import java.lang.NullPointerException
import org.junit.Test
import org.junit.Assert.*

class OptionTests {
  @Test
  fun option_canBeSome() {
    val option: String? = "Hello World"
    assertTrue(option.isSome)
    assertFalse(option.isNone)
  }

  @Test
  fun option_canBeNone() {
    val option: String? = null
    assertTrue(option.isNone)
    assertFalse(option.isSome)
  }

  @Test
  fun option_isSome_expect() {
    val option: String? = "Message"
    assertEquals(option.expect("Unneeded error message"), "Message")
  }

  @Test(expected = Exception::class)
  fun option_isNone_expect() {
    val option: String? = null
    option.expect("Error Message")
  }

  @Test
  fun option_isSome_unwrap() {
    val option: String? = "Message"
    assertEquals(option.unwrap(), "Message")
  }

  @Test(expected = NullPointerException::class)
  fun option_isNone_unwrap() {
    val option: String? = null
    option.unwrap()
  }

  @Test
  fun option_isSome_unwrapOr() {
    val option: String? = "Message"
    assertEquals(option.unwrapOr("Not This"), "Message")
  }

  @Test
  fun option_isNone_unwrapOr() {
    val option: String? = null
    assertEquals(option.unwrapOr("Message"), "Message")
  }

  @Test
  fun option_isSome_unwrapOrElse() {
    val option: String? = "Message"
    assertEquals(option.unwrapOrElse { "Not This" }, "Message")
  }

  @Test
  fun option_isNone_unwrapOrElse() {
    val option: String? = null
    assertEquals(option.unwrapOrElse { "Message" }, "Message")
  }

  @Test
  fun option_isSome_okOr() {
    val option: String? = "Message"
    assertEquals(option.okOr(NullPointerException("Not this")), Ok("Message"))
  }

  @Test
  fun option_isNone_okOr() {
    val option: String? = null
    assertEquals(
      option.okOr(NullPointerException("Message")),
      Error<String>(NullPointerException("Message")),
    )
  }

  @Test
  fun option_isSome_okOrElse() {
    val option: String? = "Message"
    assertEquals(
      option.okOrElse { NullPointerException("Not this") },
      Ok("Message"),
    )
  }

  @Test
  fun option_isNone_okOrElse() {
    val option: String? = null
    option.okOrElse { NullPointerException("Message") }
  }

  @Test
  fun option_isSome_andWithValue() {
    val option: String? = "Message"
    assertEquals(option.and("Other"), "Other")
  }

  @Test
  fun option_isNone_andWithValue() {
    val option: String? = null
    assertNull(option.and("Other"))
  }

  @Test
  fun option_isSome_andWithNull() {
    val option: String? = "Message"
    assertNull(option.and(null))
  }

  @Test
  fun option_isNone_andWithNull() {
    val option: String? = null
    assertNull(option.and(null))
  }

  @Test
  fun option_isSome_andThenWithValue() {
    val option: String? = "Message"
    assertEquals(option.andThen { "Other" }, "Other")
  }

  @Test
  fun option_isNone_andThenWithValue() {
    val option: String? = null
    assertNull(option.andThen { "Other" })
  }

  @Test
  fun option_isSome_andThenWithNull() {
    val option: String? = "Message"
    assertNull(option.andThen { null })
  }

  @Test
  fun option_isNone_andThenWithNull() {
    val option: String? = null
    assertNull(option.andThen { null })
  }

  @Test
  fun option_isSome_orWithValue() {
    val option: String? = "Message"
    assertEquals(option.or("Other"), "Message")
  }

  @Test
  fun option_isNone_orWithValue() {
    val option: String? = null
    assertEquals(option.or("Other"), "Other")
  }

  @Test
  fun option_isSome_orWithNull() {
    val option: String? = "Message"
    assertEquals(option.or(null), "Message")
  }

  @Test
  fun option_isNone_orWithNull() {
    val option: String? = null
    assertNull(option.or(null))
  }
  
  @Test
  fun option_isSome_orElseWithValue() {
    val option: String? = "Message"
    assertEquals(option.orElse { "Other" }, "Message")
  }

  @Test
  fun option_isNone_orElseWithValue() {
    val option: String? = null
    assertEquals(option.orElse { "Other" }, "Other")
  }

  @Test
  fun option_isSome_orElseWithNull() {
    val option: String? = "Message"
    assertEquals(option.orElse { null }, "Message")
  }

  @Test
  fun option_isNone_orElseWithNull() {
    val option: String? = null
    assertNull(option.orElse { null })
  }

  @Test
  fun option_isSome_xorWithValue() {
    val option: String? = "Message"
    assertNull(option.xor("Other"))
  }

  @Test
  fun option_isNone_xorWithValue() {
    val option: String? = null
    assertEquals(option.xor("Other"), "Other")
  }

  @Test
  fun option_isSome_xorWithNull() {
    val option: String? = "Message"
    assertEquals(option.xor(null), "Message")
  }

  @Test
  fun option_isNone_xorWithNull() {
    val option: String? = null
    assertNull(option.xor(null))
  }

  @Test
  fun option_isSome_toString() {
    val option: String? = "Message"
    assertEquals(option.toString(), "Message")
  }

  @Test
  fun option_isNone_toString() {
    val option: String? = null
    assertEquals(option.toString(), "null")
  }
}