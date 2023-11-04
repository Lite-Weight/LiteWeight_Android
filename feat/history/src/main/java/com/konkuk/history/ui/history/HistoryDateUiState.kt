package com.konkuk.history.ui.history

import com.konkuk.history.domain.model.HistoryCalendarModel

sealed class HistoryDateUiState {
    object Uninitialized : HistoryDateUiState()
    data class Error(val message: String) : HistoryDateUiState()
    data class Avail(val year: Int, val month: Int, val today: Int, val selectedDay: Int, val calendarList: List<HistoryCalendarModel>) : HistoryDateUiState()
}
