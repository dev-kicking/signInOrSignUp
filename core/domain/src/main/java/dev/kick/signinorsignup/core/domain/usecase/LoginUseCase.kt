package dev.kick.signinorsignup.core.domain.usecase

import dev.kick.signinorsignup.core.domain.model.User
import dev.kick.signinorsignup.core.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): User? {
        return authRepository.login(
            email = email,
            password = password,
        )
    }
}
