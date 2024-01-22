package com.nikasov.di

import com.nikasov.data.repository.RecordRepositoryImpl
import com.nikasov.data.room.RecordDao
import com.nikasov.domain.manager.MediaStorageManager
import com.nikasov.domain.repository.RecordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideRecordRepository(recordDao: RecordDao, mediaStorageManager: MediaStorageManager): RecordRepository =
        RecordRepositoryImpl(recordDao, mediaStorageManager)

}