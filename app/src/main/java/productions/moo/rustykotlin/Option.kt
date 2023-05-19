package productions.moo.rustykotlin

sealed class Option<T> {
  /**
   * Returns true if [Option] is [Some].
   */
  val isSome: Boolean get() = this is Some<*>
  /**
   * Returns true if [Option] is [None].
   */
  val isNone: Boolean get() = this is None<*>

  /**
   * Returns the contained [Some] value.
   * @param message Error message if [Option] is a [None]
   * @throws NullPointerException if the [Option] in a [None]
   */
  fun expect(message: String) : T {
    when (this) {
      is Some<T> -> return this.value
      is None<*> -> throw NullPointerException(message)
    }
  }

  /**
   * Returns the contained [Some] value.
   * Because this method may throw an exception, its use is generally discouraged. Instead, prefer
   * to use when matching to handle the [None] case explicitly, or call [unwrapOr] or
   * [unwrapOrElse].
   * @throws NullPointerException if the [Option] in a [None]
   */
  fun unwrap() : T {
    when (this) {
      is Some<T> -> return this.value
      is None<*> -> throw NullPointerException("Unwrapped None")
    }
  }

  /**
   * Returns the contained [Some] value or the provided default.
   * Arguments passed to [unwrapOr] are eagerly evaluated; if you are passing the result of a
   * function call, it is recommended to use [unwrapOrElse], which is lazily evaluated.
   */
  fun unwrapOr(default: T) : T {
    return when (this) {
      is Some<T> -> this.value
      is None<*> -> default
    }
  }

  /**
   * Returns the contained [Some] value or computes it from a lambda.
   */
  fun unwrapOrElse(defaultFun: () -> T) : T {
    return when (this) {
      is Some<T> -> this.value
      is None<*> -> defaultFun()
    }
  }

  /**
   * Transforms the [Option] into a [Result] mapping [Some] to [Ok] and [None] to [Error].
   * Arguments passed to [okOr] are eagerly evaluated; if you are passing the result of a function
   * call, it is recommended to use [okOrElse], which is lazily evaluated.
   */
  fun okOr(error: Throwable) : Result<T> {
    return when (this) {
      is Some<T> -> Ok(this.value)
      is None<*> -> Error(error)
    }
  }

  /**
   * Transforms the [Option] into a [Result] mapping [Some] to [Ok] and [None] to [Error].
   */
  fun okOrElse(errorFun: () -> Throwable) : Result<T> {
    return when (this) {
      is Some<T> -> Ok(this.value)
      is None<*> -> Error(errorFun())
    }
  }

  /**
   * Returns [None] when the [Option] is [None], otherwise returns [optionB].
   * Arguments passed to [and] are eagerly evaluated; if you are passing the result of a function
   * call, it is recommended to use [andThen], which is lazily evaluated.
   */
  fun <U> and(optionB: Option<U>) : Option<U> {
    return when(this) {
      is Some<*> -> optionB
      is None<*> -> None()
    }
  }

  /**
   * Returns [None] when the [Option] is [None], otherwise calls [thenFun] with the wrapped value
   * and returns the result.
   */
  fun <U> andThen(thenFun: (T) -> Option<U>) : Option<U> {
    return when(this) {
      is Some<T> -> thenFun(this.value)
      is None<*> -> None()
    }
  }

  /**
   * Returns the [Option] if it contains a value, otherwise returns [optionB].
   * Arguments passed to [or] are eagerly evaluated; if you are passing the result of a function
   * call, it is recommended to use [orElse], which is lazily evaluated.
   */
  fun or(optionB: Option<T>) : Option<T> {
    return when (this) {
      is Some<T> -> this
      is None<*> -> optionB
    }
  }

  /**
   * Returns the [Option] if it contains a value, otherwise calls [optionFun] and returns the value.
   */
  fun orElse(optionFun: () -> Option<T>) : Option<T> {
    return when (this) {
      is Some<T> -> this
      is None<*> -> optionFun()
    }
  }

  /**
   * Returns [Some] if exactly one of the [Option], [optionB] is [Some], otherwise returns [None].
   */
  fun xor(optionB: Option<T>) : Option<T> {
    return when (this) {
      is Some<T> -> when(optionB) {
        is Some<*> -> None()
        is None<*> -> this
      }
      is None<*> -> optionB
    }
  }
}

class Some<T>(val value: T) : Option<T>() {
  override fun toString(): String {
    return "Some: $value"
  }
}

class None<T> : Option<T>() {
  override fun toString(): String {
    return "None"
  }
}