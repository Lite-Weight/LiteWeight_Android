package com.konkuk.history.domain.repository

import com.konkuk.common.data.FoodInfo
import com.konkuk.history.domain.model.HistoryItemModel
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getFoodHistory(
        year: Int,
        month: Int,
        selectedDay: Int,
    ): Result<Flow<List<HistoryItemModel>>>

    suspend fun getFoodInfo(id: Long): Result<FoodInfo>
}
