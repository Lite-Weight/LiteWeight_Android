package com.konkuk.personal.ui.personal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.personal.domain.usecase.GetCaloriesUseCase
import com.konkuk.personal.domain.usecase.GetNutritionUseCase
import com.konkuk.personal.domain.usecase.GetWeeklyNutritionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor(
    private val getCaloriesUseCase: GetCaloriesUseCase,
    private val getNutritionUseCase: GetNutritionUseCase,
    private val getWeeklyNutritionUseCase: GetWeeklyNutritionUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow(PersonalUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        collectWeeklyCalories()
        collectCalories()
        collectNutrition()
    }

    private fun collectWeeklyCalories() {
        viewModelScope.launch {
            getWeeklyNutritionUseCase().onSuccess { weeklyList ->
                _uiState.value = _uiState.value.copy(
                    weeklyCaloriesUiState = WeeklyCaloriesUiState.Avail(
                        (_uiState.value.weeklyCaloriesUiState as? WeeklyCaloriesUiState.Avail)?.let {
                            it.weeklyCaloriesList.mapIndexed { index, pair ->
                                if (index == 0) pair else weeklyList[index]
                            }
                        } ?: weeklyList,
                    ),
                )
            }.onFailure {
                _uiState.value =
                    _uiState.value.copy(caloriesUiState = CaloriesUiState.Error(it.message.toString()))
            }
        }
    }

    private fun collectNutrition() {
        viewModelScope.launch {
            getNutritionUseCase().onSuccess { value ->
                value.collect { (car, protein, fat) ->
                    _uiState.value =
                        _uiState.value.copy(
                            nutritionUiState = NutritionUiState.Avail(
                                car.toInt(),
                                protein.toInt(),
                                fat.toInt(),
                            ),
                        )
                }
            }.onFailure {
                _uiState.value =
                    _uiState.value.copy(caloriesUiState = CaloriesUiState.Error(it.message.toString()))
            }
        }
    }

    private fun collectCalories() {
        viewModelScope.launch {
            getCaloriesUseCase().onSuccess { value ->
                value.collect { calories ->
                    _uiState.value =
                        _uiState.value.copy(
                            caloriesUiState = CaloriesUiState.InProgress(
                                progress = (calories * 100) / 2000,
                                calories = calories,
                            ),
                            weeklyCaloriesUiState = WeeklyCaloriesUiState.Avail(
                                mutableListOf<Pair<Int, Int>>().apply {
                                    add(
                                        Pair(
                                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                                            calories,
                                        ),
                                    )
                                }.apply {
                                    addAll(
                                        (_uiState.value.weeklyCaloriesUiState as? WeeklyCaloriesUiState.Avail)
                                            ?.weeklyCaloriesList?.subList(1, 7)
                                            ?: List(6) { Pair(0, 0) },
                                    )
                                },
                            ),
                        )
                }
            }.onFailure {
                _uiState.value =
                    _uiState.value.copy(caloriesUiState = CaloriesUiState.Error(it.message.toString()))
            }
        }
    }
}
