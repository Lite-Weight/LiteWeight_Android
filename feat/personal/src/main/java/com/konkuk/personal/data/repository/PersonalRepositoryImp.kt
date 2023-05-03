package com.konkuk.personal.data.repository

import com.konkuk.personal.data.datasource.PersonalDataSource
import com.konkuk.personal.domain.repository.PersonalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonalRepositoryImp @Inject constructor(
    private val personalDataSource: PersonalDataSource,
) : PersonalRepository {
    override fun getCalories(): Flow<Int> {
        return personalDataSource.getCalories()
    }
}
