package com.konkuk.history.ui.history.statistic

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.common.R
import com.konkuk.common.data.UserRepository
import com.konkuk.history.data.datasource.statistic.NutritionStat
import com.konkuk.history.data.datasource.statistic.StatCSVParser
import com.konkuk.history.domain.model.HistoryItemModel
import com.konkuk.history.domain.usecase.GetHistoryListUseCase
import com.konkuk.history.domain.usecase.GetMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val getHistoryListUseCase: GetHistoryListUseCase,
    private val getMonthUseCase: GetMonthUseCase,
    private val parser: StatCSVParser,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val statList = MutableStateFlow(NutritionStat(0, 0, 0, 0, 0, 0))
    private val nutList = MutableStateFlow(
        HistoryItemModel(
            0L, 0L, "", 0, 0, 0, 0, 0, 0,
        ),
    )

    val date = MutableStateFlow("-월 -일의 분석")

    private val _gender = MutableStateFlow(GENDER.NONE)
    val gender get() = _gender.asStateFlow()

    private val _age = MutableStateFlow(0)
    val age get() = _age.asStateFlow()

    val dataList = MutableStateFlow<List<StaticsItemModel>>(emptyList())

    init {
        initMyStat(
            savedStateHandle.get<Int>(SELECTED_YEAR_KEY)!!,
            savedStateHandle.get<Int>(SELECTED_MONTH_KEY)!!,
            savedStateHandle.get<Int>(SELECTED_DAY_KEY)!!,
        )
        initAvgStat()
        initDateList()

        viewModelScope.launch {
            _gender.value =
                if (userRepository.genderFlow.first() == true) GENDER.MALE else GENDER.FEMALE
            _age.value = userRepository.ageFlow.first()?.let { it } ?: 23
        }
    }

    private fun initDateList() {
        viewModelScope.launch {
            combine(nutList, statList) { my, avg ->
                val list = mutableListOf<StaticsItemModel>()
                val ageRange =
                    NutritionStat.ageList[NutritionStat.getAgeIndex(_age.value)].let { "${it.first}-${it.last}" }
                list.add(
                    StaticsItemModel(
                        "칼로리",
                        avg.calories,
                        my.calories,
                        ageRange,
                        "kcal",
                        _gender.value,
                    ),
                )
                list.add(
                    StaticsItemModel(
                        "탄수화물",
                        avg.carbohydrates,
                        my.carbohydrates,
                        ageRange,
                        "g",
                        _gender.value,
                    ),
                )
                list.add(
                    StaticsItemModel(
                        "단백질",
                        avg.protein,
                        my.protein,
                        ageRange,
                        "g",
                        _gender.value,
                    ),
                )
                list.add(
                    StaticsItemModel(
                        "지방",
                        avg.fat,
                        my.fat,
                        ageRange,
                        "g",
                        _gender.value,
                    ),
                )
                list.add(
                    StaticsItemModel(
                        "당류",
                        avg.sugar,
                        my.sugar,
                        ageRange,
                        "g",
                        _gender.value,
                    ),
                )
                list.add(
                    StaticsItemModel(
                        "나트륨",
                        avg.sodium,
                        my.sodium,
                        ageRange,
                        "mg",
                        _gender.value,
                    ),
                )
                list
            }.collect {
                dataList.value = it
            }
        }
    }

    private fun initAvgStat() {
        viewModelScope.launch {
            combine(_gender, _age) { (g, a) -> Pair(g, a) }.collect {
                parser.getItemList(if (_gender.value == GENDER.MALE) R.raw.edit_avg_male else R.raw.edit_avg_femial)
                    .onSuccess {
                        statList.value = it[NutritionStat.getAgeIndex(_age.value)]
                    }
            }
        }
    }

    private fun initMyStat(year: Int, month: Int, selectedDay: Int) {
        date.value = "${getMonthUseCase()}월 ${selectedDay}일의 분석"
        viewModelScope.launch {
            val historyList =
                getHistoryListUseCase(year, month, selectedDay).getOrNull()?.first()
            if (historyList?.size == 0) return@launch
            nutList.value = historyList!!.reduce { item, sum ->
                HistoryItemModel(
                    0L,
                    0L,
                    "",
                    item.calories + sum.calories,
                    item.protein + sum.protein,
                    item.carbohydrates + sum.carbohydrates,
                    item.fat + sum.fat,
                    item.sugar + sum.sugar,
                    item.sodium + sum.sodium,
                )
            }
        }
    }

    companion object {
        const val SELECTED_DAY_KEY = "SELECTED_DAY_KEY"
        const val SELECTED_MONTH_KEY = "SELECTED_MONTH_KEY"
        const val SELECTED_YEAR_KEY = "SELECTED_YEAR_KEY"
    }
}

enum class GENDER {
    MALE, FEMALE, NONE
}
