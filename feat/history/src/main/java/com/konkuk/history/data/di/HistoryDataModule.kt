package com.konkuk.history.data.di

import com.konkuk.common.data.FoodInfoDao
import com.konkuk.history.data.datasource.HistoryDataSource
import com.konkuk.history.data.repository.HistoryRepositoryImp
import com.konkuk.history.domain.repository.HistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HistoryDataModule {

    @Provides
    @Singleton
    fun provideHistoryDataSource(foodInfoDao: FoodInfoDao): HistoryDataSource {
        return HistoryDataSource(foodInfoDao)
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(HistoryDataSource: HistoryDataSource): HistoryRepository {
        return HistoryRepositoryImp(HistoryDataSource)
    }
}
