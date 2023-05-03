package com.konkuk.personal.data.di

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
class PersonalModule {

    @Provides
    @Singleton
    fun providePersonalDataSource(): PersonalDataSource {
        return PersonalDataSource()
    }

    @Provides
    @Singleton
    fun providePersonalRepository(personalDataSource: PersonalDataSource): PersonalRepository {
        return PersonalRepositoryImp(personalDataSource)
    }
}
