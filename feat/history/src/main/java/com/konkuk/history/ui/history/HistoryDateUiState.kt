package com.konkuk.history.ui.history

sealed class HistoryDateUiState {
    object Uninitialized : HistoryDateUiState()
    data class Error(val message: String) : HistoryDateUiState()
    data class Avail(val today: Int, val selectedDay: Int) : HistoryDateUiState()
}
