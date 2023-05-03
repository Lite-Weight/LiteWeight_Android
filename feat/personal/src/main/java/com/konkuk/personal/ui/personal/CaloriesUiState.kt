package com.konkuk.personal.ui.personal

sealed class CaloriesUiState {
    object Uninitialized : CaloriesUiState()
    object Error : CaloriesUiState()
    data class InProgress(val progress: Int) : CaloriesUiState()
}
