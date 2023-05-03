package com.konkuk.personal.data.datasource

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PersonalDataSource {
    fun getCalories(): Flow<Int> {
        return flow {
            repeat(100) {
                delay(20)
                emit(it + 1)
            }
        }
    }
}
