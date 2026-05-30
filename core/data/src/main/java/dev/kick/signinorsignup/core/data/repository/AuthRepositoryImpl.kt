package dev.kick.signinorsignup.core.data.repository

import dev.kick.signinorsignup.core.data.local.UserDao
import dev.kick.signinorsignup.core.data.local.UserEntity
import dev.kick.signinorsignup.core.data.mapper.toDomain
import dev.kick.signinorsignup.core.domain.model.User
import dev.kick.signinorsignup.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
) : AuthRepository {
    override suspend fun findUserByEmail(email: String): User? {
        return userDao.findByEmail(email)?.toDomain()
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
        userDao.upsert(entity)
        return entity.toDomain()
    }

    override suspend fun login(
        email: String,
        password: String,
    ): User? {
        return userDao.findByEmailAndPassword(
            email = email,
            password = password,
        )?.toDomain()
    }
}
