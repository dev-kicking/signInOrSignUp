package dev.kick.signinorsignup.feature.auth.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelResId: Int,
    modifier: Modifier = Modifier,
    @StringRes placeholderResId: Int? = null,
    @StringRes helperTextResId: Int? = null,
    @StringRes errorMessageResId: Int? = null,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyLarge,
        label = {
            Text(
                text = stringResource(id = labelResId),
                style = MaterialTheme.typography.labelMedium,
            )
        },
        placeholder = placeholderResId?.let { resId ->
            {
                Text(
                    text = stringResource(id = resId),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        },
        singleLine = true,
        enabled = enabled,
        isError = errorMessageResId != null,
        supportingText = when {
            errorMessageResId != null -> {
                {
                    Text(
                        text = stringResource(id = errorMessageResId),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
            helperTextResId != null -> {
                {
                    Text(
                        text = stringResource(id = helperTextResId),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            else -> null
        },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
    )
}
