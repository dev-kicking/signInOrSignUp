package dev.kick.signinorsignup.core.data.source.local

import dev.kick.signinorsignup.core.data.local.UserDao
import dev.kick.signinorsignup.core.data.local.UserEntity
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao,
) : AuthLocalDataSource {
    override suspend fun findUserByEmail(email: String): UserEntity? {
        return userDao.findByEmail(email)
    }

    override suspend fun findUserByEmailAndPassword(
        email: String,
        password: String,
    ): UserEntity? {
        return userDao.findByEmailAndPassword(
            email = email,
            password = password,
        )
    }

    override suspend fun saveUser(user: UserEntity) {
        userDao.upsert(user)
    }
}
