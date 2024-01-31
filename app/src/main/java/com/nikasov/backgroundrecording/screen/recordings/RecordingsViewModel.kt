package com.nikasov.backgroundrecording.screen.recordings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikasov.common.manager.audioManager.AudioManager
import com.nikasov.domain.entity.AppRecord
import com.nikasov.domain.manager.MediaDataSyncManager
import com.nikasov.domain.repository.RecordRepository
import com.nikasov.domain.usecase.RemoveRecordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordingsViewModel @Inject constructor(
    recordRepository: RecordRepository,
    private val audioManager: AudioManager,
    private val mediaDataSyncManager: MediaDataSyncManager,
    private val removeRecordUseCase: RemoveRecordUseCase
) : ViewModel() {

    val records = recordRepository.recordings.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun play(record: AppRecord) {
        audioManager.play(record.uri)
    }

    fun remove(record: AppRecord) {
        viewModelScope.launch { removeRecordUseCase(record) }
    }

    fun sync() {
        viewModelScope.launch {
            mediaDataSyncManager.sync()
        }
    }

}