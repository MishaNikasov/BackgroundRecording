package com.nikasov.backgroundrecording.screen.record

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nikasov.backgroundrecording.service.RecordEvent
import com.nikasov.backgroundrecording.service.RecordService
import com.nikasov.common.manager.recordManager.RecordingState.InProgress
import com.nikasov.common.manager.recordManager.RecordingState.Paused
import com.nikasov.common.manager.recordManager.RecordingState.Stopped
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun RecordScreen(
    navigator: DestinationsNavigator,
    viewModel: RecordViewModel = hiltViewModel(),
) {
    val state by viewModel.recordingState.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        val context = LocalContext.current
        Icon(Icons.Default.PlayArrow, contentDescription = "Start recording", modifier = Modifier.clickable {
            when(state) {
                InProgress -> RecordService.sendEvent(context, RecordEvent.Pause)
                Stopped -> RecordService.sendEvent(context, RecordEvent.Start)
                Paused -> RecordService.sendEvent(context, RecordEvent.Resume)
            }
        })
        AnimatedVisibility(visible = state != Stopped) {
            Spacer(modifier = Modifier.height(16.dp))
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