package dev.kick.signinorsignup.feature.auth.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AuthBaseScaffold(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = 24.dp,
        vertical = 32.dp,
    ),
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding),
    ) {
        Spacer(modifier = Modifier.height(56.dp))
        AuthTitleSection(
            title = title,
            description = description,
            titleStyle = MaterialTheme.typography.headlineLarge,
        )
        Spacer(modifier = Modifier.height(40.dp))
        content()
    }
}
