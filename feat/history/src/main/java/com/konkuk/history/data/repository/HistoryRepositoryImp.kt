package com.konkuk.history.data.repository

import com.konkuk.common.data.FoodInfo
import com.konkuk.history.data.datasource.HistoryDataSource
import com.konkuk.history.domain.model.HistoryItemModel
import com.konkuk.history.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryRepositoryImp @Inject constructor(
    private val historyDataSource: HistoryDataSource,
) : HistoryRepository {
    override fun getFoodHistory(
        year: Int,
        month: Int,
        selectedDay: Int,
    ): Result<Flow<List<HistoryItemModel>>> {
        return historyDataSource.getFoodHistory(year, month, selectedDay)
    }

    override suspend fun getFoodInfo(id: Long): Result<FoodInfo> {
        return historyDataSource.getFoodInfo(id)
    }
}
