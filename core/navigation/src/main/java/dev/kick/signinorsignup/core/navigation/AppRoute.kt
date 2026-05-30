package dev.kick.signinorsignup.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data object AuthGraph

@Serializable
data object AuthEmail

@Serializable
data class Login(
    val email: String,
)

@Serializable
data class SignupEmail(
    val email: String = "",
)

@Serializable
data class SignupName(
    val email: String,
)

@Serializable
data class SignupPassword(
    val email: String,
    val name: String,
)

@Serializable
data class SignupComplete(
    val email: String,
    val name: String,
)
