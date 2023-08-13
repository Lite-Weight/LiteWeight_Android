package com.konkuk.history.ui.history.statistic

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.common.R
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
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val statList = MutableStateFlow(NutritionStat(0, 0, 0, 0, 0, 0))
    private val nutList = MutableStateFlow(
        HistoryItemModel(
            0L, 0L, "", 0, 0, 0, 0, 0, 0,
        ),
    )

    val date = MutableStateFlow("-월 -일의 분석")

    private val _gender = MutableStateFlow(GENDER.MALE)
    val gender get() = _gender.asStateFlow()

    private val _age = MutableStateFlow(18)
    val age get() = _age.asStateFlow()

    val dataList = MutableStateFlow<List<StaticsItemModel>>(emptyList())

    init {
        initMyStat(savedStateHandle.get<Int>(SELECTED_DAY_KEY)!!)
        initAvgStat()
        initDateList()
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
                    ),
                )
                list.add(
                    StaticsItemModel(
                        "탄수화물",
                        avg.carbohydrates,
                        my.carbohydrates,
                        ageRange,
                        "g",
                    ),
                )
                list.add(
                    StaticsItemModel(
                        "단백질",
                        avg.protein,
                        my.protein,
                        ageRange,
                        "g",
                    ),
                )
                list.add(StaticsItemModel("지방", avg.fat, my.fat, ageRange, "g"))
                list.add(StaticsItemModel("당류", avg.sugar, my.sugar, ageRange, "g"))
                list.add(
                    StaticsItemModel(
                        "나트륨",
                        avg.sodium,
                        my.sodium,
                        ageRange,
                        "mg",
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

    private fun initMyStat(selectedDay: Int?) {
        date.value = "${getMonthUseCase()}월 ${selectedDay}일의 분석"
        viewModelScope.launch {
            val historyList =
                getHistoryListUseCase(requireNotNull(selectedDay)).getOrNull()?.first()
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

    fun changeAge(age: Int) {
        if (age > 0) _age.value = age
    }

    fun changeGender() {
        _gender.value = if (gender.value == GENDER.MALE) GENDER.FEMALE else GENDER.MALE
    }

    companion object {
        const val SELECTED_DAY_KEY = "SELECTED_DAY_KEY"
    }
}

enum class GENDER {
    MALE, FEMALE
}
