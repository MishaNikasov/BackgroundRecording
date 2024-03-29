package com.nikasov.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecordEntity::class], version = 1)
abstract class RecordDatabase: RoomDatabase() {
    abstract fun recordDao(): RecordDao
}