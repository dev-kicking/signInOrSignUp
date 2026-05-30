package dev.kick.signinorsignup.core.domain.usecase

import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): Boolean {
        if (password.length < MIN_PASSWORD_LENGTH) return false

        val conditionCount = listOf(
            password.any(Char::isUpperCase),
            password.any(Char::isLowerCase),
            password.any(Char::isDigit),
            password.any { !it.isLetterOrDigit() },
        ).count { it }

        return conditionCount >= MIN_PASSWORD_CONDITION_COUNT
    }

    private companion object {
        const val MIN_PASSWORD_LENGTH = 8
        const val MIN_PASSWORD_CONDITION_COUNT = 3
    }
}
