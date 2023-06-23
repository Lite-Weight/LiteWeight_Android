package com.konkuk.personal.ui.personal

sealed class WeeklyCaloriesUiState {
    object Uninitialized : WeeklyCaloriesUiState()
    data class Error(val message: String) : WeeklyCaloriesUiState()
    data class Avail(val weeklyCaloriesList: List<Pair<Int, Int>>) : WeeklyCaloriesUiState()
}
