package dev.kick.signinorsignup.core.data.repository

import dev.kick.signinorsignup.core.data.local.UserEntity
import dev.kick.signinorsignup.core.data.mapper.toDomain
import dev.kick.signinorsignup.core.data.source.local.AuthLocalDataSource
import dev.kick.signinorsignup.core.domain.model.User
import dev.kick.signinorsignup.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val localDataSource: AuthLocalDataSource,
) : AuthRepository {
    override suspend fun findUserByEmail(email: String): User? {
        return localDataSource.findUserByEmail(email)?.toDomain()
    }

    override suspend fun registerUser(
        email: String,
        name: String,
        password: String,
    ): User {
        val entity = UserEntity(
            email = email,
            name = name,
            password = password,
        )
        localDataSource.saveUser(entity)
        return entity.toDomain()
    }

    override suspend fun login(
        email: String,
        password: String,
    ): User? {
        return localDataSource.findUserByEmailAndPassword(
            email = email,
            password = password,
        )?.toDomain()
    }
}
