package com.konkuk.personal.ui.personal

data class PersonalUiState(
    val caloriesUiState: CaloriesUiState = CaloriesUiState.Uninitialized,
    val nutritionUiState: NutritionUiState = NutritionUiState.Uninitialized,
    val weeklyCaloriesUiState: WeeklyCaloriesUiState = WeeklyCaloriesUiState.Uninitialized,
)
