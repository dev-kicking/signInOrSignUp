package dev.kick.signinorsignup.feature.auth.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.kick.signinorsignup.feature.auth.R
import dev.kick.signinorsignup.feature.auth.component.AuthBaseScaffold
import dev.kick.signinorsignup.feature.auth.component.AuthPrimaryButton
import dev.kick.signinorsignup.feature.auth.component.AuthTextField
import dev.kick.signinorsignup.feature.auth.model.AuthIntent
import dev.kick.signinorsignup.feature.auth.model.AuthUiState

@Composable
fun AuthEmailScreen(
    uiState: AuthUiState,
    onIntent: (AuthIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    AuthBaseScaffold(
        title = stringResource(id = R.string.auth_email_title),
        description = stringResource(id = R.string.auth_email_description),
        modifier = modifier,
    ) {
        AuthTextField(
            value = uiState.email,
            onValueChange = { onIntent(AuthIntent.EmailChanged(it)) },
            labelResId = R.string.auth_label_email,
            placeholderResId = R.string.auth_placeholder_email,
            errorMessageResId = uiState.emailErrorMessageResId,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        Spacer(modifier = Modifier.height(24.dp))
        AuthPrimaryButton(
            textResId = R.string.auth_action_login_signup,
            onClick = { onIntent(AuthIntent.EmailSubmitClicked) },
            enabled = uiState.isEmailSubmitEnabled,
        )
    }
}
