package dev.kick.signinorsignup.feature.auth.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import dev.kick.signinorsignup.feature.auth.R
import dev.kick.signinorsignup.feature.auth.component.AuthPrimaryButton
import dev.kick.signinorsignup.feature.auth.component.SignupStepScaffold
import dev.kick.signinorsignup.feature.auth.model.AuthIntent

@Composable
fun SignupCompleteScreen(
    name: String,
    onLoginClick: () -> Unit,
    onIntent: (AuthIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    BackHandler(enabled = true) {
    }

    val completeTitle = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append(name)
            append(stringResource(id = R.string.auth_signup_complete_title_name_suffix))
        }
        append("\n")
        append(stringResource(id = R.string.auth_signup_complete_title_body))
    }

    SignupStepScaffold(
        title = completeTitle,
        description = stringResource(id = R.string.auth_signup_complete_description),
        currentStep = 3,
        onBackClick = { onIntent(AuthIntent.BackClicked) },
        showBackButton = false,
        modifier = modifier,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        AuthPrimaryButton(
            textResId = R.string.auth_action_confirm,
            onClick = onLoginClick,
        )
    }
}
