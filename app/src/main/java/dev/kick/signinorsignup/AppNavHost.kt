package dev.kick.signinorsignup

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import dev.kick.signinorsignup.core.navigation.AppNavigator
import dev.kick.signinorsignup.core.navigation.rememberAppNavigator
import dev.kick.signinorsignup.feature.auth.navigation.authNavGraph

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navigator: AppNavigator = rememberAppNavigator(),
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        modifier = modifier,
    ) {
        authNavGraph(
            navigateToLogin = navigator::navigateToLogin,
            navigateToSignupEmail = navigator::navigateToSignupEmail,
            navigateToSignupName = navigator::navigateToSignupName,
            navigateToSignupPassword = navigator::navigateToSignupPassword,
            navigateToSignupComplete = navigator::navigateToSignupComplete,
            navigateBack = navigator::navigateBack,
        )
    }
}
