package dev.kick.signinorsignup.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class AppNavigator(
    val navController: NavHostController,
) {
    val startDestination = AuthGraph

    fun navigateToLogin(email: String) {
        navController.navigate(Login(email = email))
    }

    fun navigateToSignupEmail(email: String) {
        navController.navigate(SignupEmail(email = email))
    }

    fun navigateToSignupName(email: String) {
        navController.navigate(SignupName(email = email))
    }

    fun navigateToSignupPassword(
        email: String,
        name: String,
    ) {
        navController.navigate(
            SignupPassword(
                email = email,
                name = name,
            ),
        )
    }

    fun navigateToSignupComplete(name: String) {
        navController.navigate(SignupComplete(name = name))
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}

@Composable
fun rememberAppNavigator(
    navController: NavHostController = rememberNavController(),
): AppNavigator = remember(navController) {
    AppNavigator(navController)
}
