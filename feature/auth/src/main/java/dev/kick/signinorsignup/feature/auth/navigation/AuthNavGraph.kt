package dev.kick.signinorsignup.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.kick.signinorsignup.feature.auth.AuthScreen

fun NavGraphBuilder.authNavGraph() {
    composable<Auth> {
        AuthScreen()
    }
}
