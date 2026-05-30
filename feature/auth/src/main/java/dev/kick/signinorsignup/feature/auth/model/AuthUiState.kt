package dev.kick.signinorsignup.feature.auth.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    @StringRes val emailErrorMessageResId: Int? = null,
    @StringRes val passwordErrorMessageResId: Int? = null,
    @StringRes val nameErrorMessageResId: Int? = null,
    val isLoading: Boolean = false,
) {
    val isEmailSubmitEnabled: Boolean = email.isNotBlank() && !isLoading
    val isSignupEmailSubmitEnabled: Boolean = email.isNotBlank() && !isLoading
    val isLoginEnabled: Boolean = password.isNotBlank() && !isLoading
    val isNameSubmitEnabled: Boolean = name.isNotBlank() && !isLoading
    val isSignupSubmitEnabled: Boolean = password.isNotBlank() && !isLoading
}
