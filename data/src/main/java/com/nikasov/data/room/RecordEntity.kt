package com.nikasov.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record")
data class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: Long,
    val filePath: String,
    val name: String
)

