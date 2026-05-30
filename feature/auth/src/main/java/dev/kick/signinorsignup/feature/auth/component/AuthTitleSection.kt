package dev.kick.signinorsignup.feature.auth.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun AuthTitleSection(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.headlineMedium,
) {
    AuthTitleSection(
        title = AnnotatedString(title),
        description = description,
        modifier = modifier,
        titleStyle = titleStyle,
    )
}

@Composable
fun AuthTitleSection(
    title: AnnotatedString,
    description: String,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.headlineMedium,
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = titleStyle,
            color = MaterialTheme.colorScheme.onBackground,
        )
        if (description.isNotBlank()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
            )
        }
    }
}
