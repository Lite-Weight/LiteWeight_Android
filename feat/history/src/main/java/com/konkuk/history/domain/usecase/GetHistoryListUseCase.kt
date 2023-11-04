package com.konkuk.history.domain.usecase

import com.konkuk.history.domain.model.HistoryItemModel
import com.konkuk.history.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class GetHistoryListUseCase @Inject constructor(
    private val historyRepository: HistoryRepository,
) {
    operator fun invoke(
        year: Int? = null,
        month: Int? = null,
        selectedDay: Int,
    ): Result<Flow<List<HistoryItemModel>>> {
        val date = Date(System.currentTimeMillis())
        return historyRepository.getFoodHistory(
            year ?: (date.year + 1900),
            month ?: (date.month + 1),
            selectedDay,
        )
    }
}
