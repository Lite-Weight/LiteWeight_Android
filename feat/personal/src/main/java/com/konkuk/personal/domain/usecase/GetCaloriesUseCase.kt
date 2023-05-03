package com.konkuk.personal.domain.usecase

import com.konkuk.personal.domain.repository.PersonalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCaloriesUseCase @Inject constructor(
    private val personalRepository: PersonalRepository,
) {
    operator fun invoke(): Flow<Int> {
        return personalRepository.getCalories()
    }
}
