package dev.kick.signinorsignup.core.domain.usecase

import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    operator fun invoke(email: String): Boolean {
        return EMAIL_REGEX.matches(email)
    }

    private companion object {
        val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$")
    }
}
