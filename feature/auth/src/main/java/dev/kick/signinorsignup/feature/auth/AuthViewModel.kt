package dev.kick.signinorsignup.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kick.signinorsignup.core.domain.usecase.CheckEmailExistsUseCase
import dev.kick.signinorsignup.core.domain.usecase.LoginUseCase
import dev.kick.signinorsignup.core.domain.usecase.RegisterUserUseCase
import dev.kick.signinorsignup.core.domain.usecase.ValidateEmailUseCase
import dev.kick.signinorsignup.core.domain.usecase.ValidateNameUseCase
import dev.kick.signinorsignup.core.domain.usecase.ValidatePasswordUseCase
import dev.kick.signinorsignup.feature.auth.R
import dev.kick.signinorsignup.feature.auth.model.AuthIntent
import dev.kick.signinorsignup.feature.auth.model.AuthSideEffect
import dev.kick.signinorsignup.feature.auth.model.AuthUiState
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val checkEmailExistsUseCase: CheckEmailExistsUseCase,
    private val loginUseCase: LoginUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
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
            AuthIntent.SignupEmailSubmitClicked -> submitSignupEmail()
            is AuthIntent.PasswordChanged -> updatePassword(intent.password)
            AuthIntent.PasswordClearClicked -> updatePassword("")
            AuthIntent.LoginClicked -> login()
            is AuthIntent.NameChanged -> updateName(intent.name)
            AuthIntent.SignupNameSubmitClicked -> submitSignupName()
            AuthIntent.SignupPasswordSubmitClicked -> register()
            AuthIntent.BackClicked -> moveBack()
        }
    }

    private fun updateEmail(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                emailErrorMessageResId = null,
            )
        }
    }

    private fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password, passwordErrorMessageResId = null) }
    }

    private fun updateName(name: String) {
        _uiState.update { it.copy(name = name, nameErrorMessageResId = null) }
    }

    private fun submitEmail() {
        val email = uiState.value.email

        if (!validateEmailUseCase(email)) {
            _uiState.update {
                it.copy(emailErrorMessageResId = R.string.auth_error_invalid_email)
            }
            return
        }

        viewModelScope.launch {
            runWithLoading {
                if (checkEmailExistsUseCase(email)) {
                    _sideEffect.send(AuthSideEffect.NavigateToLogin(email = email))
                } else {
                    _sideEffect.send(AuthSideEffect.NavigateToSignupEmail(email = email))
                }
            }
        }
    }

    private fun submitSignupEmail() {
        val email = uiState.value.email

        if (!validateEmailUseCase(email)) {
            _uiState.update {
                it.copy(emailErrorMessageResId = R.string.auth_error_invalid_email)
            }
            return
        }

        viewModelScope.launch {
            _sideEffect.send(AuthSideEffect.NavigateToSignupName(email = email))
        }
    }

    private fun login() {
        val state = uiState.value

        if (!validatePasswordUseCase(state.password)) {
            _uiState.update { it.copy(passwordErrorMessageResId = R.string.auth_error_invalid_password) }
            return
        }

        viewModelScope.launch {
            runWithLoading {
                val user = loginUseCase(
                    email = state.email,
                    password = state.password,
                )

                if (user == null) {
                    _uiState.update { it.copy(passwordErrorMessageResId = R.string.auth_error_wrong_password) }
                } else {
                    _sideEffect.send(AuthSideEffect.ShowMessage(R.string.auth_message_login_success))
                }
            }
        }
    }

    private fun submitSignupName() {
        val name = uiState.value.name

        if (!validateNameUseCase(name)) {
            _uiState.update { it.copy(nameErrorMessageResId = R.string.auth_error_empty_name) }
            return
        }

        viewModelScope.launch {
            _sideEffect.send(
                AuthSideEffect.NavigateToSignupPassword(
                    email = uiState.value.email,
                    name = name,
                ),
            )
        }
    }

    private fun register() {
        val state = uiState.value

        if (!validatePasswordUseCase(state.password)) {
            _uiState.update { it.copy(passwordErrorMessageResId = R.string.auth_error_invalid_password) }
            return
        }

        viewModelScope.launch {
            runWithLoading {
                registerUserUseCase(
                    email = state.email,
                    name = state.name,
                    password = state.password,
                )
                _sideEffect.send(AuthSideEffect.NavigateToSignupComplete(name = state.name))
            }
        }
    }

    private fun moveBack() {
        viewModelScope.launch {
            _sideEffect.send(AuthSideEffect.NavigateBack)
        }
    }

    private suspend fun runWithLoading(block: suspend () -> Unit) {
        _uiState.update { it.copy(isLoading = true) }
        runCatching { block() }
            .onFailure { _sideEffect.send(AuthSideEffect.ShowMessage(R.string.auth_message_unknown_error)) }
        _uiState.update { it.copy(isLoading = false) }
    }
}
