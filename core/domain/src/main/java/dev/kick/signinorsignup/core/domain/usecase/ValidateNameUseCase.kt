package dev.kick.signinorsignup.core.domain.usecase

import javax.inject.Inject

class ValidateNameUseCase @Inject constructor() {
    operator fun invoke(name: String): Boolean {
        return name.isNotBlank()
    }
}
