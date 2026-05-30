package dev.kick.signinorsignup.feature.auth.model

import androidx.compose.runtime.Immutable

@Immutable
data class AuthUiState(
    val step: AuthStep = AuthStep.Email,
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val emailErrorMessage: String? = null,
    val helperMessage: String? = null,
    val isLoading: Boolean = false,
) {
    val isEmailSubmitEnabled: Boolean = email.isNotBlank() && !isLoading
    val isLoginEnabled: Boolean = password.isNotBlank() && !isLoading
    val isNameSubmitEnabled: Boolean = name.isNotBlank() && !isLoading
    val isSignupSubmitEnabled: Boolean = password.isNotBlank() && !isLoading
}
