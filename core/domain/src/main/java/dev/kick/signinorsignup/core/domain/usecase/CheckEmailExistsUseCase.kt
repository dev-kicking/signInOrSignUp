package dev.kick.signinorsignup.core.domain.usecase

import dev.kick.signinorsignup.core.domain.repository.AuthRepository

class CheckEmailExistsUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String): Boolean {
        return authRepository.findUserByEmail(email) != null
    }
}
