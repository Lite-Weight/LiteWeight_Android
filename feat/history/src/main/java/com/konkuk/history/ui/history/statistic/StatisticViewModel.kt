package com.konkuk.history.ui.history.statistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.common.R
import com.konkuk.history.data.datasource.statistic.NutritionStat
import com.konkuk.history.data.datasource.statistic.StatCSVParser
import com.konkuk.history.domain.model.HistoryItemModel
import com.konkuk.history.domain.usecase.GetHistoryDateUseCase
import com.konkuk.history.domain.usecase.GetHistoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val getHistoryListUseCase: GetHistoryListUseCase,
    private val getHistoryDateUseCase: GetHistoryDateUseCase,
    private val parser: StatCSVParser,

) : ViewModel() {
    private val statList = MutableStateFlow(
        NutritionStat(
            0,
            0,
            0,
            0,
            0,
            0,
        ),
    )
    private val nutList = MutableStateFlow(
        HistoryItemModel(
            0L, 0L, "", 0, 0, 0, 0, 0, 0,
        ),
    )

    val gender = MutableStateFlow(GENDER.MALE)
    val age = MutableStateFlow(18)
    val dataList = MutableStateFlow<List<StaticsItemModel>>(emptyList())

    init {
        initMyStat()
        initAvgStat()
        initDateList()
    }

    private fun initDateList() {
        viewModelScope.launch {
            combine(nutList, statList) { my, avg ->
                val list = mutableListOf<StaticsItemModel>()
                val ageRange =
                    NutritionStat.ageList[NutritionStat.getAgeIndex(age.value)].let { "${it.first}-${it.last}" }
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
                        my.fat,
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
            combine(gender, age) { (g, a) -> Pair(g, a) }.collect {
                parser.getItemList(if (gender.value == GENDER.MALE) R.raw.edit_avg_male else R.raw.edit_avg_femial)
                    .onSuccess {
                        statList.value = it[NutritionStat.getAgeIndex(age.value)]
                    }
            }
        }
    }

    private fun initMyStat() {
        viewModelScope.launch {
            val date = getHistoryDateUseCase().getOrNull()?.first()
            val historyList = getHistoryListUseCase(requireNotNull(date)).getOrNull()?.first()
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
}

enum class GENDER {
    MALE, FEMALE
}
