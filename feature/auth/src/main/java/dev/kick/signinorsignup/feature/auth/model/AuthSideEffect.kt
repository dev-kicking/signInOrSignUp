package dev.kick.signinorsignup.feature.auth.model

sealed interface AuthSideEffect {
    data class ShowMessage(val message: String) : AuthSideEffect
}
