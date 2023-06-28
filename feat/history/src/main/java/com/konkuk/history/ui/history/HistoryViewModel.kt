package com.konkuk.history.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.common.data.FoodInfo
import com.konkuk.history.domain.model.HistoryCalendarModel
import com.konkuk.history.domain.usecase.GetFoodInfoByIdUseCase
import com.konkuk.history.domain.usecase.GetHistoryDateUseCase
import com.konkuk.history.domain.usecase.GetHistoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryDateUseCase: GetHistoryDateUseCase,
    private val getHistoryListUseCase: GetHistoryListUseCase,
    private val getFoodInfoByIdUseCase: GetFoodInfoByIdUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow(HistoryUiState())
    val uiState get() = _uiState.asStateFlow()

    private var job: Job? = null

    init {
        initFoodHistory()
    }

    private fun initCalendar(): List<HistoryCalendarModel> {
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.set(Calendar.DAY_OF_MONTH, 1) // 이번 달의 1일로 설정

        val totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
        val dayFormat = SimpleDateFormat("E", Locale.getDefault())
        val calendarList = mutableListOf<HistoryCalendarModel>()

        for (i in 1..totalDays) {
            val date = dateFormat.format(calendar.time)
            val dayOfWeek = dayFormat.format(calendar.time)

            calendarList.add(HistoryCalendarModel(date, dayOfWeek, i == today))

            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return calendarList
    }

    private fun initFoodHistory() {
        getHistoryDateUseCase().onSuccess { value ->
            viewModelScope.launch {
                val today = value.first()
                _uiState.value = _uiState.value.copy(
                    historyDateUiState = HistoryDateUiState.Avail(today, today, initCalendar()),
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
            job?.cancel()
            job = Job()
            CoroutineScope((job ?: Job())).launch {
                value.collect { historyItemModelList ->
                    _uiState.value = _uiState.value.copy(
                        historyListUiState = HistoryListUiState.Avail(list = historyItemModelList),
                    )
                }
            }
        }.onFailure {
            _uiState.value =
                _uiState.value.copy(historyListUiState = HistoryListUiState.Error(it.message.toString()))
        }
    }

    fun selectDay(selectedDay: Int) {
        if (_uiState.value.historyDateUiState is HistoryDateUiState.Avail) {
            val beforeSelected =
                (_uiState.value.historyDateUiState as HistoryDateUiState.Avail).selectedDay

            _uiState.value = _uiState.value.copy(
                historyDateUiState = (_uiState.value.historyDateUiState as HistoryDateUiState.Avail).copy(
                    selectedDay = selectedDay,
                    calendarList = (_uiState.value.historyDateUiState as HistoryDateUiState.Avail).calendarList.toMutableList()
                        .apply {
                            this[beforeSelected - 1] =
                                this[beforeSelected - 1].copy(isSelected = false)
                            this[selectedDay - 1] = this[selectedDay - 1].copy(isSelected = true)
                        },
                ),
            )
            getHistoryList(selectedDay)
        }
    }

    private fun getHistoryList(selectedDay: Int) {
        getHistoryListUseCase(selectedDay).onSuccess { value ->
            job?.cancel()
            job = Job()
            CoroutineScope((job ?: Job())).launch {
                value.collect { historyItemModelList ->
                    _uiState.value = _uiState.value.copy(
                        historyListUiState = HistoryListUiState.Avail(list = historyItemModelList),
                    )
                }
            }
        }.onFailure {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(
                    historyListUiState = HistoryListUiState.Error(it.message.toString()),
                )
            }
        }
    }

    fun getFoodInfo(id: Long, onGetSuccess: (FoodInfo) -> Unit) {
        viewModelScope.launch {
            getFoodInfoByIdUseCase(id).onSuccess { foodInfo ->
                onGetSuccess(foodInfo)
            }
        }
    }

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }
}
