package com.nikasov.backgroundrecording.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nikasov.backgroundrecording.R
import com.nikasov.common.manager.recordManager.RecordingState

enum class RecordButtonCallback {
    Start,
    Resume,
    Pause
}

@Composable
fun RecordBtn(
    state: RecordingState,
    onClick: (RecordButtonCallback) -> Unit,
    modifier: Modifier = Modifier,
) {

    val icon = when (state) {
        RecordingState.InProgress -> painterResource(id = R.drawable.ic_pause)
        RecordingState.Stopped -> painterResource(id = R.drawable.ic_record)
        RecordingState.Paused -> painterResource(id = R.drawable.ic_resume)
    }

    Box(
        modifier = modifier
            .size(120.dp)
    ) {
        if (state == RecordingState.InProgress) {
            val infiniteTransition = rememberInfiniteTransition("Infinite transition")
            val scale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1300),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "Scale animation"
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .scale(scale)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f))
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .clickable {
                    val callback = when (state) {
                        RecordingState.InProgress -> RecordButtonCallback.Pause
                        RecordingState.Stopped -> RecordButtonCallback.Start
                        RecordingState.Paused -> RecordButtonCallback.Resume
                    }
                    onClick(callback)
                }
        ) {
            Icon(
                painter = icon,
                contentDescription = "Start recording",
                tint = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            )
        }
    }

}