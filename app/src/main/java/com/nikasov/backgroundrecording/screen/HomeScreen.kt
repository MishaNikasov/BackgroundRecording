package com.nikasov.backgroundrecording.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nikasov.common.recordManager.RecordingState.InProgress
import com.nikasov.common.recordManager.RecordingState.Paused
import com.nikasov.common.recordManager.RecordingState.Stopped

@Composable
fun HomeScreen(
    viewModel: RecordViewModel = hiltViewModel(),
) {
    val state by viewModel.recordingState.collectAsState()
    Box(
        modifier = Modifier
//            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
//                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
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
                    onClick = { viewModel.stopRecording() }
                ) {
                    Text("Stop")
                }
            }
        }
    }

}