package dev.kick.signinorsignup.core.data.mapper

import dev.kick.signinorsignup.core.data.local.UserEntity
import dev.kick.signinorsignup.core.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        email = email,
        name = name,
    )
}
