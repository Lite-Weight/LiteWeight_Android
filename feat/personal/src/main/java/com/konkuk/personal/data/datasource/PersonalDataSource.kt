package com.konkuk.personal.data.datasource

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PersonalDataSource {
    fun getCalories(): Result<Flow<Int>> {
        return Result.success(
            flow {
                repeat(100) {
                    delay(20)
                    emit(it * 20)
                }
            },
        )
    }
}
