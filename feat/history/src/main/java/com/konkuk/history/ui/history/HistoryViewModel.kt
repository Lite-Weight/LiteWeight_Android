package com.konkuk.history.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.history.domain.usecase.GetHistoryDateUseCase
import com.konkuk.history.domain.usecase.GetHistoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryDateUseCase: GetHistoryDateUseCase,
    private val getHistoryListUseCase: GetHistoryListUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow(HistoryUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        initFoodHistory()
    }

    private fun initFoodHistory() {
        getHistoryDateUseCase().onSuccess { value ->
            viewModelScope.launch {
                val today = value.first()
                _uiState.value = _uiState.value.copy(
                    historyDateUiState = HistoryDateUiState.Avail(today, today),
                )
                initHistoryList(today)
            }
        }.onFailure {
            _uiState.value = _uiState.value.copy(
                historyDateUiState = HistoryDateUiState.Error(it.message.toString()),
                historyListUiState = HistoryListUiState.Error("date error"), // todo 에러 리소스 빼기
            )
        }
    }

    private fun initHistoryList(selectedDay: Int) {
        getHistoryListUseCase(selectedDay).onSuccess { value ->
            viewModelScope.launch {
                _uiState.value =
                    _uiState.value.copy(historyListUiState = HistoryListUiState.Avail(value.first()))
            }
        }.onFailure {
            _uiState.value =
                _uiState.value.copy(historyListUiState = HistoryListUiState.Error(it.message.toString()))
        }
    }

    fun selectDay(selectedDay: Int) {
        if (_uiState.value.historyDateUiState is HistoryDateUiState.Avail) {
            _uiState.value = _uiState.value.copy(
                historyDateUiState = (_uiState.value.historyDateUiState as HistoryDateUiState.Avail).copy(
                    selectedDay = selectedDay,
                ),
            )
            getHistoryList(selectedDay)
        }
    }

    private fun getHistoryList(selectedDay: Int) {
        getHistoryListUseCase(selectedDay).onSuccess { value ->
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(
                    historyListUiState = HistoryListUiState.Avail(value.first()),
                )
            }
        }.onFailure {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(
                    historyListUiState = HistoryListUiState.Error(it.message.toString()),
                )
            }
        }
    }
}
