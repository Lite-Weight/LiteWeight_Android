package com.konkuk.personal.domain.repository

import kotlinx.coroutines.flow.Flow

interface PersonalRepository {
    fun getCalories(): Result<Flow<Int>>
}
