package com.nikasov.di

import com.nikasov.data.repository.RecordRepositoryImpl
import com.nikasov.domain.repository.RecordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideRecordRepository(recordRepositoryImpl: RecordRepositoryImpl): RecordRepository

}