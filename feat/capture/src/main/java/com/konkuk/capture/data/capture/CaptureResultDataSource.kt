package com.konkuk.capture.data.capture

import com.konkuk.common.data.FoodInfo
import com.konkuk.common.data.FoodInfoDao
import javax.inject.Inject

class CaptureResultDataSource @Inject constructor(private val foodInfoDao: FoodInfoDao) {
    suspend fun insert(foodInfo: FoodInfo): Result<FoodInfo> {
        return kotlin.runCatching {
            foodInfoDao.insert(foodInfo)
            foodInfo
        }
    }

    suspend fun delete(foodInfo: FoodInfo) {
        foodInfoDao.delete(foodInfo)
    }
}
