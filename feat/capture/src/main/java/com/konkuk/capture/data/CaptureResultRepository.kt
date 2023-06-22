package com.konkuk.capture.data

import com.konkuk.common.data.FoodInfo
import javax.inject.Inject

class CaptureResultRepository @Inject constructor(private val captureResultDataSource: CaptureResultDataSource) {
    suspend fun insert(foodInfo: FoodInfo): Result<FoodInfo> {
        return captureResultDataSource.insert(foodInfo)
    }
}
