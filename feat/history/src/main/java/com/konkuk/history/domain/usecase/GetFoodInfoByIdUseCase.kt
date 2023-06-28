package com.konkuk.history.domain.usecase

import com.konkuk.common.data.FoodInfo
import com.konkuk.history.domain.repository.HistoryRepository
import javax.inject.Inject

class GetFoodInfoByIdUseCase @Inject constructor(
    private val historyRepository: HistoryRepository,
) {
    suspend operator fun invoke(id: Long): Result<FoodInfo> {
        return historyRepository.getFoodInfo(id)
    }
}
