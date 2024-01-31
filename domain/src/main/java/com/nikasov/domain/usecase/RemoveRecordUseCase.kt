package com.nikasov.domain.usecase

import com.nikasov.domain.entity.AppRecord
import com.nikasov.domain.mediaStorage.MediaStorageManager
import com.nikasov.domain.repository.RecordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoveRecordUseCase @Inject constructor(
    private val mediaStorageManager: MediaStorageManager,
    private val recordRepository: RecordRepository
) {

    suspend operator fun invoke(appRecord: AppRecord) = withContext(Dispatchers.IO) {
        mediaStorageManager.removeMedia(appRecord.uri)
        recordRepository.deleteRecord(appRecord.id)
    }

}