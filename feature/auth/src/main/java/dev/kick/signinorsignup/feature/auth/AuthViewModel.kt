package dev.kick.signinorsignup.feature.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kick.signinorsignup.core.domain.usecase.ValidateEmailUseCase
import dev.kick.signinorsignup.feature.auth.model.AuthIntent
import dev.kick.signinorsignup.feature.auth.model.AuthSideEffect
import dev.kick.signinorsignup.feature.auth.model.AuthStep
import dev.kick.signinorsignup.feature.auth.model.AuthUiState
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _sideEffect = Channel<AuthSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.EmailChanged -> updateEmail(intent.email)
            AuthIntent.EmailClearClicked -> updateEmail("")
            AuthIntent.EmailSubmitClicked -> submitEmail()
            is AuthIntent.PasswordChanged -> updatePassword(intent.password)
            AuthIntent.PasswordClearClicked -> updatePassword("")
            AuthIntent.LoginClicked -> Unit
            is AuthIntent.NameChanged -> updateName(intent.name)
            AuthIntent.SignupNameSubmitClicked -> moveTo(AuthStep.SignupPassword)
            AuthIntent.SignupPasswordSubmitClicked -> moveTo(AuthStep.SignupComplete)
            AuthIntent.BackClicked -> moveBack()
        }
    }

    private fun updateEmail(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                emailErrorMessage = null,
                helperMessage = null,
            )
        }
    }

    private fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    private fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    private fun submitEmail() {
        val email = uiState.value.email

        if (!validateEmailUseCase(email)) {
            _uiState.update {
                it.copy(emailErrorMessage = "이메일 형식이 올바르지 않습니다.")
            }
            return
        }

        _uiState.update {
            it.copy(
                step = AuthStep.LoginPassword,
                helperMessage = "이메일이 확인되었습니다. :)",
            )
        }
    }

    private fun moveTo(step: AuthStep) {
        _uiState.update { it.copy(step = step) }
    }

    private fun moveBack() {
        _uiState.update {
            it.copy(
                step = when (it.step) {
                    AuthStep.Email -> AuthStep.Email
                    AuthStep.LoginPassword -> AuthStep.Email
                    AuthStep.SignupEmailConfirmed -> AuthStep.Email
                    AuthStep.SignupName -> AuthStep.SignupEmailConfirmed
                    AuthStep.SignupPassword -> AuthStep.SignupName
                    AuthStep.SignupComplete -> AuthStep.SignupPassword
                },
            )
        }
    }
}
