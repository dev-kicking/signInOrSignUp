package dev.kick.signinorsignup.core.data.source.local

import dev.kick.signinorsignup.core.data.local.UserEntity

interface AuthLocalDataSource {
    suspend fun findUserByEmail(email: String): UserEntity?

    suspend fun findUserByEmailAndPassword(
        email: String,
        password: String,
    ): UserEntity?

    suspend fun saveUser(user: UserEntity)
}
