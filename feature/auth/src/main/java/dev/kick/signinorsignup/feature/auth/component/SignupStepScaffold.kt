package dev.kick.signinorsignup.feature.auth.component

import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import dev.kick.signinorsignup.feature.auth.R

@Composable
fun SignupStepScaffold(
    title: String,
    description: String,
    currentStep: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = 24.dp,
        vertical = 16.dp,
    ),
    content: @Composable ColumnScope.() -> Unit,
) {
    SignupStepScaffold(
        title = AnnotatedString(title),
        description = description,
        currentStep = currentStep,
        onBackClick = onBackClick,
        modifier = modifier,
        showBackButton = showBackButton,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun SignupStepScaffold(
    title: AnnotatedString,
    description: String,
    currentStep: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = 24.dp,
        vertical = 16.dp,
    ),
    content: @Composable ColumnScope.() -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
    ) {
        SignupStepTopBar(
            onBackClick = onBackClick,
            showBackButton = showBackButton,
            modifier = Modifier.padding(
                start = contentPadding.calculateStartPadding(layoutDirection),
                top = contentPadding.calculateTopPadding(),
                end = contentPadding.calculateEndPadding(layoutDirection),
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        SignupStepProgress(
            currentStep = currentStep,
            modifier = Modifier.fillMaxWidth(),
        )
        Column(
            modifier = Modifier.padding(contentPadding),
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            AuthTitleSection(
                title = title,
                description = description,
            )
            Spacer(modifier = Modifier.height(40.dp))
            content()
        }
    }
}

@Composable
private fun SignupStepTopBar(
    onBackClick: () -> Unit,
    showBackButton: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (showBackButton) {
            val iconColor = MaterialTheme.colorScheme.onBackground
            val backDescription = stringResource(id = R.string.auth_content_description_back)
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(48.dp)
                    .semantics {
                        contentDescription = backDescription
                    },
            ) {
                Canvas(modifier = Modifier.size(24.dp)) {
                    drawLine(
                        color = iconColor,
                        start = Offset(size.width, size.height / 2),
                        end = Offset(4.dp.toPx(), size.height / 2),
                        strokeWidth = 2.dp.toPx(),
                        cap = StrokeCap.Round,
                    )
                    drawLine(
                        color = iconColor,
                        start = Offset(4.dp.toPx(), size.height / 2),
                        end = Offset(11.dp.toPx(), 5.dp.toPx()),
                        strokeWidth = 2.dp.toPx(),
                        cap = StrokeCap.Round,
                    )
                    drawLine(
                        color = iconColor,
                        start = Offset(4.dp.toPx(), size.height / 2),
                        end = Offset(11.dp.toPx(), size.height - 5.dp.toPx()),
                        strokeWidth = 2.dp.toPx(),
                        cap = StrokeCap.Round,
                    )
                }
            }
        }
    }
}

@Composable
private fun SignupStepProgress(
    currentStep: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(4.dp)
            .clip(RoundedCornerShape(50)),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            repeat(SIGNUP_STEP_COUNT) { index ->
                val isFilled = index < currentStep
                val color = if (isFilled) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .background(color),
                )
            }
        }
    }
}

private const val SIGNUP_STEP_COUNT = 3
