package dev.kick.signinorsignup.feature.auth.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.kick.signinorsignup.feature.auth.R
import dev.kick.signinorsignup.feature.auth.component.AuthPrimaryButton
import dev.kick.signinorsignup.feature.auth.component.AuthTextField
import dev.kick.signinorsignup.feature.auth.component.SignupStepScaffold
import dev.kick.signinorsignup.feature.auth.model.AuthIntent
import dev.kick.signinorsignup.feature.auth.model.AuthUiState

@Composable
fun SignupNameScreen(
    uiState: AuthUiState,
    onIntent: (AuthIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    SignupStepScaffold(
        title = stringResource(id = R.string.auth_signup_name_title),
        description = stringResource(id = R.string.auth_signup_name_description),
        currentStep = 1,
        onBackClick = { onIntent(AuthIntent.BackClicked) },
        modifier = modifier,
    ) {
        AuthTextField(
            value = uiState.name,
            onValueChange = { onIntent(AuthIntent.NameChanged(it)) },
            labelResId = R.string.auth_label_name,
            errorMessageResId = uiState.nameErrorMessageResId,
        )
        Spacer(modifier = Modifier.height(24.dp))
        AuthPrimaryButton(
            textResId = R.string.auth_action_next,
            onClick = { onIntent(AuthIntent.SignupNameSubmitClicked) },
            enabled = uiState.isNameSubmitEnabled,
        )
    }
}
