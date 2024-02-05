package com.nikasov.di

import com.nikasov.common.manager.audioManager.AudioManager
import com.nikasov.common.manager.audioManager.AudioManagerImpl
import com.nikasov.common.manager.notification.NotificationManager
import com.nikasov.common.manager.notification.NotificationManagerImpl
import com.nikasov.common.manager.recordManager.RecordManager
import com.nikasov.common.manager.recordManager.RecordManagerImpl
import com.nikasov.data.manager.MediaDataSyncManagerImpl
import com.nikasov.data.mediaStorage.MediaStorageManagerImpl
import com.nikasov.data.storage.AppStorageImpl
import com.nikasov.domain.manager.MediaDataSyncManager
import com.nikasov.domain.mediaStorage.MediaStorageManager
import com.nikasov.domain.storage.AppStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    @Binds
    @Singleton
    abstract fun provideAudioManager(audioManagerImpl: AudioManagerImpl): AudioManager

    @Binds
    @Singleton
    abstract fun provideRecordManager(recordManagerImpl: RecordManagerImpl): RecordManager

    @Binds
    @Singleton
    abstract fun provideMediaStorageManager(mediaStorageManagerImpl: MediaStorageManagerImpl): MediaStorageManager

    @Binds
    @Singleton
    abstract fun provideAppStorage(appStorageImpl: AppStorageImpl): AppStorage

    @Binds
    @Singleton
    abstract fun provideMediaDataSyncManager(mediaDataSyncManager: MediaDataSyncManagerImpl): MediaDataSyncManager

    @Binds
    abstract fun provideNotificationManager(notificationManagerImpl: NotificationManagerImpl): NotificationManager

}