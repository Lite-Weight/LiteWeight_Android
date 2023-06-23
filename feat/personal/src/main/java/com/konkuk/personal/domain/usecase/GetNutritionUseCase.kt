package com.konkuk.personal.domain.usecase

import com.konkuk.personal.domain.repository.PersonalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNutritionUseCase @Inject constructor(private val personalRepository: PersonalRepository) {
    operator fun invoke(): Result<Flow<Triple<Float, Float, Float>>> {
        return personalRepository.getNutrition()
    }
}
