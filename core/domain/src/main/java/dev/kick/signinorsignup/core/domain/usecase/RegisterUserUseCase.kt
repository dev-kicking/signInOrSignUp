package dev.kick.signinorsignup.core.domain.usecase

import dev.kick.signinorsignup.core.domain.model.User
import dev.kick.signinorsignup.core.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        name: String,
        password: String,
    ): User {
        return authRepository.registerUser(
            email = email,
            name = name,
            password = password,
        )
    }
}
