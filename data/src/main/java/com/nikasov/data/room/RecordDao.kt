package com.nikasov.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecordDao {

    @Insert
    suspend fun insertRecord(recordEntity: RecordEntity): Long

    @Delete
    suspend fun deleteRecord(recordEntity: RecordEntity)

    @Query("DELETE FROM RECORD WHERE ID == :id")
    suspend fun deleteRecordById(id: Long)

}