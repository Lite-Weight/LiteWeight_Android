package com.konkuk.history.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class GetHistoryDateUseCase @Inject constructor() {
    operator fun invoke(): Result<Flow<Int>> {
        return kotlin.runCatching {
            flow {
                val date = Date(System.currentTimeMillis())
                emit(date.day)
            }
        }
    }
}
