package com.konkuk.personal.domain.repository

import kotlinx.coroutines.flow.Flow

interface PersonalRepository {
    fun getCalories(): Result<Flow<Int>>
    fun getNutrition(): Result<Flow<Triple<Float, Float, Float>>>
    suspend fun getWeeklyNutrition(): Result<List<Int>>
}
