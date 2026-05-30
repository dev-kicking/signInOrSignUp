package dev.kick.signinorsignup.core.domain.usecase

class ValidatePasswordUseCase {
    operator fun invoke(password: String): Boolean {
        return password.length >= MIN_PASSWORD_LENGTH
    }

    private companion object {
        const val MIN_PASSWORD_LENGTH = 8
    }
}
