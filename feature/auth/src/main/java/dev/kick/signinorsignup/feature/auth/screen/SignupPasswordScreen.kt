package dev.kick.signinorsignup.feature.auth.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import dev.kick.signinorsignup.feature.auth.R
import dev.kick.signinorsignup.feature.auth.component.AuthPrimaryButton
import dev.kick.signinorsignup.feature.auth.component.AuthTextField
import dev.kick.signinorsignup.feature.auth.component.SignupStepScaffold
import dev.kick.signinorsignup.feature.auth.model.AuthIntent
import dev.kick.signinorsignup.feature.auth.model.AuthUiState

@Composable
fun SignupPasswordScreen(
    uiState: AuthUiState,
    onIntent: (AuthIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    SignupStepScaffold(
        title = stringResource(id = R.string.auth_signup_password_title),
        description = stringResource(id = R.string.auth_signup_password_description),
        currentStep = 2,
        onBackClick = { onIntent(AuthIntent.BackClicked) },
        modifier = modifier,
    ) {
        AuthTextField(
            value = uiState.password,
            onValueChange = { onIntent(AuthIntent.PasswordChanged(it)) },
            labelResId = R.string.auth_label_password,
            errorMessageResId = uiState.passwordErrorMessageResId,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )
        Spacer(modifier = Modifier.height(24.dp))
        AuthPrimaryButton(
            textResId = R.string.auth_action_signup_complete,
            onClick = { onIntent(AuthIntent.SignupPasswordSubmitClicked) },
            enabled = uiState.isSignupSubmitEnabled,
        )
    }
}
