package com.konkuk.history.data.datasource

import com.konkuk.history.domain.model.HistoryItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date

class HistoryDataSource {
    fun getFoodHistory(year: Int, month: Int, day: Int): Result<Flow<List<HistoryItemModel>>> =
        Result.success(
            flow {
                emit(
                    listOf(
                        HistoryItemModel(
                            Date(2022, 1, 2).time,
                            "콜라",
                            100,
                        ),
                        HistoryItemModel(
                            Date(2022, 1, 3).time,
                            "떡볶이",
                            200,
                        ),
                        HistoryItemModel(
                            Date(2022, 1, 4).time,
                            "마라탕",
                            150,
                        ),
                    ),
                )
            },
        )
}
