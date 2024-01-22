package com.nikasov.di

import android.content.Context
import androidx.room.Room
import com.nikasov.data.room.RecordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun provideRecordDao(recordDatabase: RecordDatabase) = recordDatabase.recordDao()

    @Provides
    @Singleton
    fun provideRecordDatabase(
        @ApplicationContext context: Context,
    ): RecordDatabase = Room.databaseBuilder(
        context,
        RecordDatabase::class.java,
        "RecordDatabase.db"
    )
        .fallbackToDestructiveMigration()
        .build()

}