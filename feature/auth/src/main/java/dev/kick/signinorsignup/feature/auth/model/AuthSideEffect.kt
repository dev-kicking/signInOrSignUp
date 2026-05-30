package dev.kick.signinorsignup.feature.auth.model

sealed interface AuthSideEffect {
    data class ShowMessage(val message: String) : AuthSideEffect
    data class NavigateToLogin(val email: String) : AuthSideEffect
    data class NavigateToSignupEmail(val email: String) : AuthSideEffect
    data class NavigateToSignupName(val email: String) : AuthSideEffect
    data class NavigateToSignupPassword(
        val email: String,
        val name: String,
    ) : AuthSideEffect

    data object NavigateToSignupComplete : AuthSideEffect
    data object NavigateBack : AuthSideEffect
}
