package com.nikasov.backgroundrecording.screen.recording

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            modifier = Modifier.weight(1f),
            onClick = {
                when (state) {
                    InProgress -> viewModel.pauseRecording()
                    Stopped -> viewModel.startRecording()
                    Paused -> viewModel.resumeRecording()
                }
            }
        ) {
            Text(
                text = when (state) {
                    InProgress -> "Pause"
                    Stopped -> "Start"
                    Paused -> "Resume"
                }
            )
        }
        Button(
            modifier = Modifier.weight(1f),
            enabled = state != Stopped,
            onClick = {
                viewModel.stopRecording()
            }
        ) {
            Text("Stop")
        }
    }
}