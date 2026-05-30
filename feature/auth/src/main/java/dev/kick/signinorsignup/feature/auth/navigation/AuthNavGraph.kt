package dev.kick.signinorsignup.feature.auth.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.kick.signinorsignup.core.navigation.AuthEmail
import dev.kick.signinorsignup.core.navigation.AuthGraph
import dev.kick.signinorsignup.core.navigation.Login
import dev.kick.signinorsignup.core.navigation.SignupComplete
import dev.kick.signinorsignup.core.navigation.SignupEmail
import dev.kick.signinorsignup.core.navigation.SignupName
import dev.kick.signinorsignup.core.navigation.SignupPassword
import dev.kick.signinorsignup.feature.auth.AuthScreen
import dev.kick.signinorsignup.feature.auth.AuthViewModel
import dev.kick.signinorsignup.feature.auth.model.AuthIntent
import dev.kick.signinorsignup.feature.auth.model.AuthSideEffect
import dev.kick.signinorsignup.feature.auth.model.AuthUiState

fun NavGraphBuilder.authNavGraph(
    navigateToLogin: (String) -> Unit,
    navigateToSignupEmail: (String) -> Unit,
    navigateToSignupName: (String) -> Unit,
    navigateToSignupPassword: (String, String) -> Unit,
    navigateToSignupComplete: () -> Unit,
    navigateBack: () -> Unit,
) {
    navigation<AuthGraph>(
        startDestination = AuthEmail,
    ) {
        composable<AuthEmail> {
            AuthRoute(
                navigateToLogin = navigateToLogin,
                navigateToSignupEmail = navigateToSignupEmail,
                navigateToSignupName = navigateToSignupName,
                navigateToSignupPassword = navigateToSignupPassword,
                navigateToSignupComplete = navigateToSignupComplete,
                navigateBack = navigateBack,
            ) { _ ->
                AuthScreen(
                    title = "이메일 입력",
                    description = "로그인 또는 회원가입을 시작합니다.",
                )
            }
        }

        composable<Login> { backStackEntry ->
            val route = backStackEntry.toRoute<Login>()

            AuthRoute(
                initialEmail = route.email,
                navigateToLogin = navigateToLogin,
                navigateToSignupEmail = navigateToSignupEmail,
                navigateToSignupName = navigateToSignupName,
                navigateToSignupPassword = navigateToSignupPassword,
                navigateToSignupComplete = navigateToSignupComplete,
                navigateBack = navigateBack,
            ) { uiState ->
                AuthScreen(
                    title = "로그인",
                    description = "${uiState.email} 계정의 비밀번호를 입력합니다.",
                )
            }
        }

        composable<SignupEmail> { backStackEntry ->
            val route = backStackEntry.toRoute<SignupEmail>()

            AuthRoute(
                initialEmail = route.email,
                navigateToLogin = navigateToLogin,
                navigateToSignupEmail = navigateToSignupEmail,
                navigateToSignupName = navigateToSignupName,
                navigateToSignupPassword = navigateToSignupPassword,
                navigateToSignupComplete = navigateToSignupComplete,
                navigateBack = navigateBack,
            ) { uiState ->
                AuthScreen(
                    title = "회원가입 이메일",
                    description = uiState.email.ifBlank { "가입에 사용할 이메일을 입력합니다." },
                )
            }
        }

        composable<SignupName> { backStackEntry ->
            val route = backStackEntry.toRoute<SignupName>()

            AuthRoute(
                initialEmail = route.email,
                navigateToLogin = navigateToLogin,
                navigateToSignupEmail = navigateToSignupEmail,
                navigateToSignupName = navigateToSignupName,
                navigateToSignupPassword = navigateToSignupPassword,
                navigateToSignupComplete = navigateToSignupComplete,
                navigateBack = navigateBack,
            ) { _ ->
                AuthScreen(
                    title = "회원가입 이름",
                    description = "사용자 이름을 입력합니다.",
                )
            }
        }

        composable<SignupPassword> { backStackEntry ->
            val route = backStackEntry.toRoute<SignupPassword>()

            AuthRoute(
                initialEmail = route.email,
                initialName = route.name,
                navigateToLogin = navigateToLogin,
                navigateToSignupEmail = navigateToSignupEmail,
                navigateToSignupName = navigateToSignupName,
                navigateToSignupPassword = navigateToSignupPassword,
                navigateToSignupComplete = navigateToSignupComplete,
                navigateBack = navigateBack,
            ) { uiState ->
                AuthScreen(
                    title = "회원가입 비밀번호",
                    description = "${uiState.name}님의 비밀번호를 입력합니다.",
                )
            }
        }

        composable<SignupComplete> {
            AuthRoute(
                navigateToLogin = navigateToLogin,
                navigateToSignupEmail = navigateToSignupEmail,
                navigateToSignupName = navigateToSignupName,
                navigateToSignupPassword = navigateToSignupPassword,
                navigateToSignupComplete = navigateToSignupComplete,
                navigateBack = navigateBack,
            ) { _ ->
                AuthScreen(
                    title = "가입 완료",
                    description = "DEEP.FINE 가입이 완료되었습니다.",
                )
            }
        }
    }
}

@Composable
private fun AuthRoute(
    initialEmail: String? = null,
    initialName: String? = null,
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToLogin: (String) -> Unit,
    navigateToSignupEmail: (String) -> Unit,
    navigateToSignupName: (String) -> Unit,
    navigateToSignupPassword: (String, String) -> Unit,
    navigateToSignupComplete: () -> Unit,
    navigateBack: () -> Unit,
    content: @Composable (AuthUiState) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(initialEmail) {
        initialEmail?.let { viewModel.handleIntent(AuthIntent.EmailChanged(it)) }
    }

    LaunchedEffect(initialName) {
        initialName?.let { viewModel.handleIntent(AuthIntent.NameChanged(it)) }
    }

    LaunchedEffect(viewModel) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is AuthSideEffect.ShowMessage -> Unit
                is AuthSideEffect.NavigateToLogin -> navigateToLogin(sideEffect.email)
                is AuthSideEffect.NavigateToSignupEmail -> navigateToSignupEmail(sideEffect.email)
                is AuthSideEffect.NavigateToSignupName -> navigateToSignupName(sideEffect.email)
                is AuthSideEffect.NavigateToSignupPassword -> {
                    navigateToSignupPassword(sideEffect.email, sideEffect.name)
                }
                AuthSideEffect.NavigateToSignupComplete -> navigateToSignupComplete()
                AuthSideEffect.NavigateBack -> navigateBack()
            }
        }
    }

    content(uiState)
}
