package com.konkuk.personal.domain.usecase

import com.konkuk.personal.domain.repository.PersonalRepository
import javax.inject.Inject

class GetWeeklyNutritionUseCase @Inject constructor(private val personalRepository: PersonalRepository) {
    suspend operator fun invoke(): Result<List<Pair<Int, Int>>> {
        return personalRepository.getWeeklyNutrition()
    }
}
