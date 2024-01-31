package com.nikasov.data.manager

import com.nikasov.domain.entity.AppRecord
import com.nikasov.domain.manager.MediaDataSyncManager
import com.nikasov.domain.mediaStorage.MediaStorageManager
import com.nikasov.domain.repository.RecordRepository
import com.nikasov.domain.storage.AppStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZoneId

class MediaDataSyncManagerImpl(
    private val mediaStorageManager: MediaStorageManager,
    private val appStorage: AppStorage,
    private val recordRepository: RecordRepository
): MediaDataSyncManager {

    override suspend fun sync() = withContext(Dispatchers.IO) {
        if (mediaStorageManager.mediaStoreVersion != appStorage.lastSyncedVersion.first()) {
            mediaStorageManager.getMediaList().forEach { item ->
                //todo move to mapper
                recordRepository.insertRecord(
                    AppRecord(
                        id = item.id,
                        date = Instant.ofEpochMilli(item.duration).atZone(ZoneId.systemDefault()).toLocalDate(),
                        name = item.name,
                        uri = item.uri,
                        duration = item.duration,
                    )
                )
            }
        }
    }

}