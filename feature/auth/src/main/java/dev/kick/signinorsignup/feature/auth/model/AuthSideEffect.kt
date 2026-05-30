package dev.kick.signinorsignup.feature.auth.model

import androidx.annotation.StringRes

sealed interface AuthSideEffect {
    data class ShowMessage(
        @StringRes val messageResId: Int,
    ) : AuthSideEffect

    data class NavigateToLogin(val email: String) : AuthSideEffect
    data class NavigateToSignupEmail(val email: String) : AuthSideEffect
    data class NavigateToSignupName(val email: String) : AuthSideEffect
    data class NavigateToSignupPassword(
        val email: String,
        val name: String,
    ) : AuthSideEffect

    data class NavigateToSignupComplete(
        val email: String,
        val name: String,
    ) : AuthSideEffect

    data object NavigateBack : AuthSideEffect
}
