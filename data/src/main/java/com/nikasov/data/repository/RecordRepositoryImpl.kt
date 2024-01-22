package com.nikasov.data.repository

import android.net.Uri
import com.nikasov.data.room.RecordDao
import com.nikasov.domain.entity.AppRecord
import com.nikasov.domain.manager.MediaStorageManager
import com.nikasov.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class RecordRepositoryImpl(
    private val recordDao: RecordDao,
    private val mediaStorageManager: MediaStorageManager,
) : RecordRepository {

    override val recordings: Flow<List<AppRecord>> = recordDao.recordList().map { list ->
        list.map { item ->
            AppRecord(
                id = item.id,
                date = LocalDate.ofEpochDay(item.date),
                name = item.name,
                uri = Uri.parse(item.fileUri),
                duration = item.duration,
            )
        }
    }

    override suspend fun deleteRecord(id: Long) {
        recordDao.deleteRecordById(id)
    }

    override suspend fun insertRecord() {
//        recordDao.insertRecord()
    }

}