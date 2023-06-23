package com.konkuk.personal.data.di

import com.konkuk.common.data.FoodInfoDao
import com.konkuk.personal.data.datasource.PersonalDataSource
import com.konkuk.personal.data.repository.PersonalRepositoryImp
import com.konkuk.personal.domain.repository.PersonalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PersonalDataModule {

    @Provides
    @Singleton
    fun providePersonalDataSource(foodInfoDao: FoodInfoDao): PersonalDataSource {
        return PersonalDataSource(foodInfoDao)
    }

    @Provides
    @Singleton
    fun providePersonalRepository(personalDataSource: PersonalDataSource): PersonalRepository {
        return PersonalRepositoryImp(personalDataSource)
    }
}
