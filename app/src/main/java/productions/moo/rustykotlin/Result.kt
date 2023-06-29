package productions.moo.rustykotlin

import kotlin.reflect.typeOf

/**
 * This class represents a return value from a function that would fail. The only two valid types
 * are [Ok] and [Error]. The type can be checked using the [isOk] and [isError] properties
 * or it can be used in a when statement:
 *
 * when (result) {
 *     is Ok<*> -> handleOk(result.value)
 *     is Error -> handleError(result.error)
 * }
 */
sealed class Result<T> {
  /**
   * Returns true if the [Result] is a [Ok].
   */
  val isOk: Boolean get() = this is Ok<*>
  /**
   * Returns true if the [Result] is a [Error].
   */
  val isError: Boolean get() = this is Error<*>

  /**
   * Returns the contained [Ok] value.
   * @param message Error message if [Result] is an [Error]
   * @throws NullPointerException if the [Result] in a [Error]
   */
  fun expect(message: String) : T {
    return when (this) {
      is Ok<T> -> this.value
      is Error<*> -> throw NullPointerException(message)
    }
  }

  /**
   * Returns the contained [Error] value.
   * @param message Error message if [Result] is an [Ok]
   * @throws NullPointerException if the [Result] in a [Ok]
   */
  fun expectError(message: String) : Throwable {
    return when (this) {
      is Ok<*> -> throw NullPointerException(message)
      is Error<*> -> this.error
    }
  }

  /**
   * Returns the contained [Ok] value.
   * Because this method may throw an exception, its use is generally discouraged. Instead, prefer
   * to use when matching to handle the [Error] case explicitly, or call [unwrapOr] or
   * [unwrapOrElse].
   * @throws Throwable if the [Result] in a [Error]
   */
  fun unwrap() : T {
    return when(this) {
      is Ok<T> -> this.value
      is Error<*> -> throw this.error
    }
  }

  /**
   * Returns the contained [Error] value.
   * @throws NullPointerException if the [Result] in a [Ok] with the message from the [Ok] value.
   */
  fun unwrapError() : Throwable {
    return when(this) {
      is Ok<T> -> throw NullPointerException(this.value.toString())
      is Error<*> -> this.error
    }
  }

  /**
   * Returns the contained [Ok] value or the provided default.
   * Arguments passed to [unwrapOr] are eagerly evaluated; if you are passing the result of a
   * function call, it is recommended to use [unwrapOrElse], which is lazily evaluated.
   */
  fun unwrapOr(default: T) : T {
    return when(this) {
      is Ok<T> -> this.value
      is Error<*> -> default
    }
  }

  /**
   * Returns the contained [Ok] value or computes it from a lambda.
   */
  fun unwrapOrElse(defaultFun: () -> T) : T {
    return when (this) {
      is Ok<T> -> this.value
      is Error<*> -> defaultFun()
    }
  }

  /**
   * Returns the error if the Result is an Error or null if it is a Ok.
   * @return The error if the Result is an Error or null if it is a Ok.
   */
  fun errorOrNull(): Throwable? {
    return when (this) {
      is Error<*> -> this.error
      is Ok<*> -> null
    }
  }
}

class Ok<T>(val value: T) : Result<T>() {
  override fun toString(): String {
    return "Ok: $value"
  }

  override fun equals(other: Any?) = (other is Ok<*>)
    && value == other.value

  override fun hashCode(): Int {
    return value.hashCode()
  }
}

class Error<T>(val error: Throwable) : Result<T>() {
  override fun toString(): String {
    return "Error: ${error.message}"
  }

  override fun equals(other: Any?) = (other is Error<*>)
    && error::class == other.error::class
    && error.message == other.error.message

  override fun hashCode(): Int {
    return error.hashCode()
  }
}