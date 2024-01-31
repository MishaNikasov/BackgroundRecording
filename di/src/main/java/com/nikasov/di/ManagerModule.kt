package com.nikasov.di

import android.content.Context
import com.nikasov.common.audioManager.AudioManager
import com.nikasov.common.audioManager.AudioManagerImpl
import com.nikasov.common.recordManager.RecordManager
import com.nikasov.common.recordManager.RecordManagerImpl
import com.nikasov.data.manager.MediaDataSyncManagerImpl
import com.nikasov.data.mediaStorage.MediaStorageManagerImpl
import com.nikasov.data.storage.AppStorageImpl
import com.nikasov.domain.manager.MediaDataSyncManager
import com.nikasov.domain.mediaStorage.MediaStorageManager
import com.nikasov.domain.repository.RecordRepository
import com.nikasov.domain.storage.AppStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ManagerModule {

    @Singleton
    @Provides
    fun provideAudioManager(@ApplicationContext context: Context): AudioManager = AudioManagerImpl(context)

    @Singleton
    @Provides
    fun provideRecordManager(@ApplicationContext context: Context): RecordManager = RecordManagerImpl(context)

    @Singleton
    @Provides
    fun provideMediaStorageManager(@ApplicationContext context: Context): MediaStorageManager = MediaStorageManagerImpl(context)

    @Singleton
    @Provides
    fun provideAppStorage(@ApplicationContext context: Context): AppStorage = AppStorageImpl(context)

    @Singleton
    @Provides
    fun provideMediaDataSyncManager(
        mediaStorageManager: MediaStorageManager,
        appStorage: AppStorage,
        recordRepository: RecordRepository
    ): MediaDataSyncManager = MediaDataSyncManagerImpl(mediaStorageManager, appStorage, recordRepository)

}