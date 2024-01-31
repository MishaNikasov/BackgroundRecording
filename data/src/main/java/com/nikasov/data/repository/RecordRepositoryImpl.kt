package com.nikasov.data.repository

import android.net.Uri
import com.nikasov.data.room.RecordDao
import com.nikasov.data.room.RecordEntity
import com.nikasov.domain.entity.AppRecord
import com.nikasov.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class RecordRepositoryImpl(
    private val recordDao: RecordDao,
) : RecordRepository {

    override val recordings: Flow<List<AppRecord>> = recordDao.recordList().map { list ->
        list
            .map { item ->
                AppRecord(
                    id = item.id,
                    date = LocalDate.ofEpochDay(item.date),
                    name = item.name,
                    uri = Uri.parse(item.fileUri),
                    duration = item.duration,
                )
            }
            .sortedBy { it.date }
    }

    override suspend fun deleteRecord(id: Long) {
        recordDao.deleteRecordById(id)
    }

    override suspend fun insertRecord(record: AppRecord) {
        with(record) {
            //todo move to mapper
            recordDao.insertRecord(
                RecordEntity(
                    id = id,
                    date = date.toEpochDay(),
                    duration = duration,
                    fileUri = uri.toString(),
                    name = name,
                )
            )
        }
    }

}