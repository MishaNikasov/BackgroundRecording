package com.nikasov.data.recordStorage

import android.net.Uri
import com.nikasov.domain.repository.entity.Media
import kotlinx.coroutines.flow.Flow

interface RecordStorageManager {
    val recordings: Flow<List<Media>>
    suspend fun createRecordUri(name: String): Uri?
    suspend fun removeRecord(uri: Uri)
}