package com.nikasov.backgroundrecording.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDelete(
    onDelete: () -> Unit,
    background: @Composable RowScope.() -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val animationTime = remember { 700 }
    var visibilityState by remember { mutableStateOf(true) }
    LaunchedEffect(visibilityState) {
        if (!visibilityState) {
            delay(animationTime.toLong())
            onDelete()
        }
    }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                visibilityState = false
                true
            } else false
        }
    )
    SwipeToDismissBox(
        state = state,
        backgroundContent = { background() },
        enableDismissFromStartToEnd = false,
        content = {
            AnimatedVisibility(
                visible = visibilityState,
                exit = shrinkVertically(
                    animationSpec = tween(animationTime),
                    shrinkTowards = Alignment.Top
                ) + fadeOut(),
            ) {
                content()
            }
        }
    )
}