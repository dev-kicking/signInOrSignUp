package dev.kick.signinorsignup.feature.auth

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AuthScreen(
    title: String,
    description: String,
) {
    Text(text = "$title\n$description")
}
