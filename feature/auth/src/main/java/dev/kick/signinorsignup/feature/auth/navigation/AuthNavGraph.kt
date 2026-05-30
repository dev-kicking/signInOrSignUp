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
import dev.kick.signinorsignup.feature.auth.AuthViewModel
import dev.kick.signinorsignup.feature.auth.model.AuthIntent
import dev.kick.signinorsignup.feature.auth.model.AuthSideEffect
import dev.kick.signinorsignup.feature.auth.model.AuthUiState
import dev.kick.signinorsignup.feature.auth.screen.AuthEmailScreen
import dev.kick.signinorsignup.feature.auth.screen.LoginScreen
import dev.kick.signinorsignup.feature.auth.screen.SignupCompleteScreen
import dev.kick.signinorsignup.feature.auth.screen.SignupEmailScreen
import dev.kick.signinorsignup.feature.auth.screen.SignupNameScreen
import dev.kick.signinorsignup.feature.auth.screen.SignupPasswordScreen

fun NavGraphBuilder.authNavGraph(
    navigateToLogin: (String) -> Unit,
    navigateToSignupEmail: (String) -> Unit,
    navigateToSignupName: (String) -> Unit,
    navigateToSignupPassword: (String, String) -> Unit,
    navigateToSignupComplete: (String) -> Unit,
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
            ) { uiState, onIntent ->
                AuthEmailScreen(
                    uiState = uiState,
                    onIntent = onIntent,
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
            ) { uiState, onIntent ->
                LoginScreen(
                    uiState = uiState,
                    onIntent = onIntent,
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
            ) { uiState, onIntent ->
                SignupEmailScreen(
                    uiState = uiState,
                    onIntent = onIntent,
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
            ) { uiState, onIntent ->
                SignupNameScreen(
                    uiState = uiState,
                    onIntent = onIntent,
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
            ) { uiState, onIntent ->
                SignupPasswordScreen(
                    uiState = uiState,
                    onIntent = onIntent,
                )
            }
        }

        composable<SignupComplete> { backStackEntry ->
            val route = backStackEntry.toRoute<SignupComplete>()

            AuthRoute(
                navigateToLogin = navigateToLogin,
                navigateToSignupEmail = navigateToSignupEmail,
                navigateToSignupName = navigateToSignupName,
                navigateToSignupPassword = navigateToSignupPassword,
                navigateToSignupComplete = navigateToSignupComplete,
                navigateBack = navigateBack,
            ) { _, onIntent ->
                SignupCompleteScreen(
                    name = route.name,
                    onIntent = onIntent,
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
    navigateToSignupComplete: (String) -> Unit,
    navigateBack: () -> Unit,
    content: @Composable (AuthUiState, (AuthIntent) -> Unit) -> Unit,
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
                is AuthSideEffect.NavigateToSignupComplete -> navigateToSignupComplete(sideEffect.name)
                AuthSideEffect.NavigateBack -> navigateBack()
            }
        }
    }

    content(uiState, viewModel::handleIntent)
}
