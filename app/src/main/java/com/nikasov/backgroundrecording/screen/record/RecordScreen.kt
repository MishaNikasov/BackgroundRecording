package com.nikasov.backgroundrecording.screen.record

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.nikasov.backgroundrecording.components.RecordBtn
import com.nikasov.backgroundrecording.components.RecordButtonCallback.Pause
import com.nikasov.backgroundrecording.components.RecordButtonCallback.Resume
import com.nikasov.backgroundrecording.components.RecordButtonCallback.Start
import com.nikasov.backgroundrecording.service.RecordEvent
import com.nikasov.backgroundrecording.service.RecordService
import com.nikasov.common.manager.recordManager.RecordingState.Stopped
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalPermissionsApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun RecordScreen(
    navigator: DestinationsNavigator,
    viewModel: RecordViewModel = hiltViewModel(),
) {
    val state by viewModel.recordingState.collectAsState()
    val context = LocalContext.current
    val recordPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO) {
        RecordService.sendEvent(context, RecordEvent.Start)
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        RecordBtn(
            state = state,
            onClick = { callback ->
                when (callback) {
                    Start -> {
                        if (recordPermissionState.status.isGranted) {
                            RecordService.sendEvent(context, RecordEvent.Start)
                        } else {
                            recordPermissionState.launchPermissionRequest()
                        }
                    }
                    Resume -> RecordService.sendEvent(context, RecordEvent.Resume)
                    Pause -> RecordService.sendEvent(context, RecordEvent.Pause)
                }
            },
            modifier = Modifier
                .align(Alignment.Center)
        )
        AnimatedVisibility(
            visible = state != Stopped,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { RecordService.sendEvent(context, RecordEvent.Cancel) }
                ) {
                    Text(text = "Cancel")
                }
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { RecordService.sendEvent(context, RecordEvent.Stop) }
                ) {
                    Text("Save")
                }
            }
        }
    }
}