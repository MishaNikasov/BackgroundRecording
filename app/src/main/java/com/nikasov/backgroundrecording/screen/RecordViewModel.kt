package com.nikasov.backgroundrecording.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikasov.common.audioManager.AudioManager
import com.nikasov.common.recordManager.RecordManager
import com.nikasov.common.recordManager.RecordingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val audioManager: AudioManager,
    private val recordManager: RecordManager,
) : ViewModel() {

    val recordingState = recordManager.state.stateIn(viewModelScope, SharingStarted.Lazily, RecordingState.Stopped)

    private var currentFile: File? = null

    fun startRecording(filePath: String) {
        currentFile = File(filePath)
        currentFile?.let { recordManager.start(it) }
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

    fun play() {
        currentFile?.let { audioManager.play(it) }
    }

}