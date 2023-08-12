package com.konkuk.capture.domain

import com.konkuk.capture.data.capture.CaptureResultRepository
import com.konkuk.common.data.FoodInfo
import javax.inject.Inject

class SaveFoodHistoryUseCase @Inject constructor(private val captureResultRepository: CaptureResultRepository) {
    suspend operator fun invoke(foodInfo: FoodInfo): Result<FoodInfo> {
        return captureResultRepository.insert(foodInfo)
    }
}
