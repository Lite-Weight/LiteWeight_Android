package com.konkuk.personal.ui.personal

sealed class CaloriesUiState {
    object Uninitialized : CaloriesUiState()
    data class Error(val message: String) : CaloriesUiState()
    data class InProgress(val progress: Int, val calories: Int) : CaloriesUiState()
}
