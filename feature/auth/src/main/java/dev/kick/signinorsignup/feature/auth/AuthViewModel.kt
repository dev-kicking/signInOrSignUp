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
                emailErrorMessage = null,
                helperMessage = null,
            )
        }
    }

    private fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password, passwordErrorMessage = null) }
    }

    private fun updateName(name: String) {
        _uiState.update { it.copy(name = name, nameErrorMessage = null) }
    }

    private fun submitEmail() {
        val email = uiState.value.email

        if (!validateEmailUseCase(email)) {
            _uiState.update {
                it.copy(emailErrorMessage = "이메일 형식이 올바르지 않습니다.")
            }
            return
        }

        viewModelScope.launch {
            runWithLoading {
                val nextStep = if (checkEmailExistsUseCase(email)) {
                    AuthStep.LoginPassword
                } else {
                    AuthStep.SignupEmailConfirmed
                }

                _uiState.update {
                    it.copy(
                        step = nextStep,
                        helperMessage = when (nextStep) {
                            AuthStep.LoginPassword -> "이메일이 확인되었습니다. :)"
                            AuthStep.SignupEmailConfirmed -> "로그인 정보가 없습니다. 회원가입을 진행할게요."
                            else -> it.helperMessage
                        },
                    )
                }
            }
        }
    }

    private fun login() {
        val state = uiState.value

        if (!validatePasswordUseCase(state.password)) {
            _uiState.update { it.copy(passwordErrorMessage = "비밀번호는 8자 이상 입력해주세요.") }
            return
        }

        viewModelScope.launch {
            runWithLoading {
                val user = loginUseCase(
                    email = state.email,
                    password = state.password,
                )

                if (user == null) {
                    _uiState.update { it.copy(passwordErrorMessage = "비밀번호가 올바르지 않습니다.") }
                } else {
                    _sideEffect.send(AuthSideEffect.ShowMessage("로그인되었습니다."))
                }
            }
        }
    }

    private fun submitSignupName() {
        val name = uiState.value.name

        if (!validateNameUseCase(name)) {
            _uiState.update { it.copy(nameErrorMessage = "이름을 입력해주세요.") }
            return
        }

        moveTo(AuthStep.SignupPassword)
    }

    private fun register() {
        val state = uiState.value

        if (!validatePasswordUseCase(state.password)) {
            _uiState.update { it.copy(passwordErrorMessage = "비밀번호는 8자 이상 입력해주세요.") }
            return
        }

        viewModelScope.launch {
            runWithLoading {
                registerUserUseCase(
                    email = state.email,
                    name = state.name,
                    password = state.password,
                )
                _uiState.update { it.copy(step = AuthStep.SignupComplete) }
            }
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

    private suspend fun runWithLoading(block: suspend () -> Unit) {
        _uiState.update { it.copy(isLoading = true) }
        runCatching { block() }
            .onFailure { _sideEffect.send(AuthSideEffect.ShowMessage("잠시 후 다시 시도해주세요.")) }
        _uiState.update { it.copy(isLoading = false) }
    }
}
