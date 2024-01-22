package com.nikasov.backgroundrecording.screen.recording

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikasov.common.recordManager.RecordManager
import com.nikasov.common.recordManager.RecordingState
import com.nikasov.domain.manager.MediaStorageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recordManager: RecordManager,
    private val mediaStorageManager: MediaStorageManager,
) : ViewModel() {

    val recordingState = recordManager.state.stateIn(viewModelScope, SharingStarted.Lazily, RecordingState.Stopped)

    fun startRecording() {
        viewModelScope.launch {
            mediaStorageManager.createMediaUri("sampleAudio.mp3")?.let { recordManager.start(it) }
        }
    }

    fun pauseRecording() {
        recordManager.pause()
    }

    fun resumeRecording() {
        recordManager.resume()
    }

    fun stopRecording() {
        recordManager.stop()
    }

}