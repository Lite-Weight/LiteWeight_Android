package com.konkuk.personal.data.repository

import com.konkuk.personal.data.datasource.PersonalDataSource
import com.konkuk.personal.domain.repository.PersonalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonalRepositoryImp @Inject constructor(
    private val personalDataSource: PersonalDataSource,
) : PersonalRepository {
    override fun getCalories(): Result<Flow<Int>> {
        return personalDataSource.getCalories()
    }

    override fun getNutrition(): Result<Flow<Triple<Float, Float, Float>>> {
        return personalDataSource.getNutrition()
    }

    override suspend fun getWeeklyNutrition(): Result<List<Pair<Int, Int>>> {
        return personalDataSource.getWeeklyNutrition()
    }
}
