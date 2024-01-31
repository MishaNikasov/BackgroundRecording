package com.nikasov.backgroundrecording.screen.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikasov.common.manager.recordManager.RecordManager
import com.nikasov.common.manager.recordManager.RecordingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(recordManager: RecordManager) : ViewModel() {

    val recordingState = recordManager.state.stateIn(viewModelScope, SharingStarted.Lazily, RecordingState.Stopped)

}