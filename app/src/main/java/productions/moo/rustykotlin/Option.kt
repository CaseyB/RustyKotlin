package productions.moo.rustykotlin

/**
 * Returns true if [Option] is [Some].
 */
val <T> T?.isSome: Boolean get() = this != null
/**
 * Returns true if [Option] is [None].
 */
val <T> T?.isNone: Boolean get() = this == null

/**
 * Returns the contained value.
 * @param message Error message if the optional is null
 * @throws NullPointerException if the optional is null
 */
fun <T> T?.expect(message: String) : T {
  return when (this) {
    null -> throw NullPointerException(message)
    else -> this
  }
}

/**
 * Returns the contained value.
 * Because this method may throw an exception, its use is generally discouraged. Instead, prefer
 * to use when matching to handle the null case explicitly, or call [unwrapOr] or
 * [unwrapOrElse].
 * @throws NullPointerException if the optional is a null
 */
fun <T> T?.unwrap() : T {
  return when (this) {
    null -> throw NullPointerException("Unwrapped null")
    else -> this
  }
}

/**
 * Returns the contained [Some] value or the provided default.
 * Arguments passed to [unwrapOr] are eagerly evaluated; if you are passing the result of a
 * function call, it is recommended to use [unwrapOrElse], which is lazily evaluated.
 */
fun <T> T?.unwrapOr(default: T) : T {
  return when (this) {
    null -> default
    else -> this
  }
}

/**
 * Returns the contained [Some] value or computes it from a lambda.
 */
fun <T> T?.unwrapOrElse(defaultFun: () -> T) : T {
  return when (this) {
    null -> defaultFun()
    else -> this
  }
}

/**
 * Transforms the [Option] into a [Result] mapping [Some] to [Ok] and [None] to [Error].
 * Arguments passed to [okOr] are eagerly evaluated; if you are passing the result of a function
 * call, it is recommended to use [okOrElse], which is lazily evaluated.
 */
fun <T> T?.okOr(error: Throwable) : Result<T> {
  return when (this) {
    null -> Error(error)
    else -> Ok(this)
  }
}

/**
 * Transforms the [Option] into a [Result] mapping [Some] to [Ok] and [None] to [Error].
 */
fun <T> T?.okOrElse(errorFun: () -> Throwable) : Result<T> {
  return when (this) {
    null -> Error(errorFun())
    else -> Ok(this)
  }
}

/**
 * Returns [None] when the [Option] is [None], otherwise returns [optionB].
 * Arguments passed to [and] are eagerly evaluated; if you are passing the result of a function
 * call, it is recommended to use [andThen], which is lazily evaluated.
 */
fun <T, U> T?.and(optionB: U?) : U? {
  return when(this) {
    null -> null
    else -> optionB
  }
}

/**
 * Returns [None] when the [Option] is [None], otherwise calls [thenFun] with the wrapped value
 * and returns the result.
 */
fun <T, U> T?.andThen(thenFun: (T) -> U?) : U? {
  return when(this) {
    null -> null
    else -> thenFun(this)
  }
}

/**
 * Returns the [Option] if it contains a value, otherwise returns [optionB].
 * Arguments passed to [or] are eagerly evaluated; if you are passing the result of a function
 * call, it is recommended to use [orElse], which is lazily evaluated.
 */
fun <T> T?.or(optionB: T) : T {
  return when (this) {
    null -> optionB
    else -> this
  }
}

/**
 * Returns the [Option] if it contains a value, otherwise calls [optionFun] and returns the value.
 */
fun <T> T?.orElse(optionFun: () -> T?) : T? {
  return when (this) {
    null -> optionFun()
    else -> this
  }
}

/**
 * Returns [Some] if exactly one of the [Option], [optionB] is [Some], otherwise returns [None].
 */
fun <T> T?.xor(optionB: T?) : T? {
  return when (this) {
    null -> optionB
    else -> when (optionB) {
      null -> this
      else -> null
    }
  }
}
