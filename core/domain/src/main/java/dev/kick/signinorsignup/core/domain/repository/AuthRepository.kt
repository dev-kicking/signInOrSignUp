package dev.kick.signinorsignup.core.domain.repository

import dev.kick.signinorsignup.core.domain.model.User

interface AuthRepository {
    suspend fun findUserByEmail(email: String): User?

    suspend fun registerUser(
        email: String,
        name: String,
        password: String,
    ): User

    suspend fun login(
        email: String,
        password: String,
    ): User?
}
