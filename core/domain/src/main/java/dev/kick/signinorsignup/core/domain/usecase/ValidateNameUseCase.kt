package dev.kick.signinorsignup.core.domain.usecase

class ValidateNameUseCase {
    operator fun invoke(name: String): Boolean {
        return name.isNotBlank()
    }
}
