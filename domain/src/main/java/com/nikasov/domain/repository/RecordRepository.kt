package com.nikasov.domain.repository

import com.nikasov.domain.entity.AppRecord
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    val recordings: Flow<List<AppRecord>>
    suspend fun deleteRecord(id: Long)
    suspend fun insertRecord(record: AppRecord)
}