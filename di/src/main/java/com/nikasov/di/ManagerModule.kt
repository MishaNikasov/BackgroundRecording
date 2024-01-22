package com.nikasov.di

import android.content.Context
import com.nikasov.common.audioManager.AudioManager
import com.nikasov.common.audioManager.AudioManagerImpl
import com.nikasov.common.recordManager.RecordManager
import com.nikasov.common.recordManager.RecordManagerImpl
import com.nikasov.data.recordStorage.RecordStorageManager
import com.nikasov.data.recordStorage.RecordStorageManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ManagerModule {

    @Provides
    fun provideAudioManager(@ApplicationContext context: Context): AudioManager = AudioManagerImpl(context)

    @Provides
    fun provideRecordManager(@ApplicationContext context: Context): RecordManager = RecordManagerImpl(context)

    @Provides
    fun provideRecordStorageManager(@ApplicationContext context: Context): RecordStorageManager = RecordStorageManagerImpl(context)

}