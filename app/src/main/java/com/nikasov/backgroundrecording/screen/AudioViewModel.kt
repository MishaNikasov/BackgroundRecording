package com.nikasov.backgroundrecording.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikasov.common.audioManager.AudioManager
import com.nikasov.data.recordStorage.RecordStorageManager
import com.nikasov.domain.repository.entity.Media
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val audioManager: AudioManager,
    private val storageManager: RecordStorageManager,
) : ViewModel() {

    val medias = storageManager.recordings.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun play(media: Media) {
        audioManager.play(media.uri)
    }

    fun remove(media: Media) {
        viewModelScope.launch {
            storageManager.removeRecord(media.uri)
        }
    }

}