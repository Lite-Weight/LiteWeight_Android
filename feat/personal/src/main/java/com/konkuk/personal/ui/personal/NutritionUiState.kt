package com.konkuk.personal.ui.personal

sealed class NutritionUiState {
    object Uninitialized : NutritionUiState()
    data class Error(val message: String) : NutritionUiState()
    data class Avail(val carbohydrates: Int, val protein: Int, val fat: Int) : NutritionUiState()
}
