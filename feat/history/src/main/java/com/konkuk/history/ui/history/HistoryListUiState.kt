package com.konkuk.history.ui.history

import com.konkuk.history.domain.model.HistoryItemModel

sealed class HistoryListUiState {
    object Uninitialized : HistoryListUiState()
    data class Error(val message: String) : HistoryListUiState()
    data class Avail(val list: List<HistoryItemModel>) : HistoryListUiState()
}
