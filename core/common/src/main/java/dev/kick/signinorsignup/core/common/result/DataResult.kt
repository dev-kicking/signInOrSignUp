package dev.kick.signinorsignup.core.common.result

sealed interface DataResult<out T> {
    data class Success<T>(val data: T) : DataResult<T>
    data class Failure(val throwable: Throwable) : DataResult<Nothing>
}
