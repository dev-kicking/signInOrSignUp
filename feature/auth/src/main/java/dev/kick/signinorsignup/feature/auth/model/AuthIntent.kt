package dev.kick.signinorsignup.feature.auth.model

import androidx.compose.runtime.Immutable

@Immutable
sealed interface AuthIntent {
    data class EmailChanged(val email: String) : AuthIntent
    data object EmailClearClicked : AuthIntent
    data object EmailSubmitClicked : AuthIntent
    data class PasswordChanged(val password: String) : AuthIntent
    data object PasswordClearClicked : AuthIntent
    data object LoginClicked : AuthIntent
    data class NameChanged(val name: String) : AuthIntent
    data object SignupNameSubmitClicked : AuthIntent
    data object SignupPasswordSubmitClicked : AuthIntent
    data object BackClicked : AuthIntent
}
