package com.nikasov.data.recordStorage

import android.net.Uri
import com.nikasov.domain.repository.entity.Media

interface RecordStorageManager {
    fun getRecordingsList(): List<Media>
    fun createRecordUri(name: String): Uri?
    fun removeRecord(uri: Uri)
}