package com.konkuk.history.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import javax.inject.Inject

class GetHistoryDateUseCase @Inject constructor() {
    operator fun invoke(): Result<Flow<Int>> {
        return kotlin.runCatching {
            flow {
                emit(Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
            }
        }
    }
}
