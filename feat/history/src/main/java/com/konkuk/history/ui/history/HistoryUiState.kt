package com.konkuk.history.ui.history

data class HistoryUiState(
    val historyDateUiState: HistoryDateUiState = HistoryDateUiState.Uninitialized,
    val historyListUiState: HistoryListUiState = HistoryListUiState.Uninitialized,
)
