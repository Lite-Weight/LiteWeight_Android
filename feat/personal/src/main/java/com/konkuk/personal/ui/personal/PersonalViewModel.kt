package com.konkuk.personal.ui.personal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.personal.domain.usecase.GetCaloriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor(
    private val getCaloriesUseCase: GetCaloriesUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow(PersonalUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        collectCalories()
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
                        )
                }
            }.onFailure {
                _uiState.value =
                    _uiState.value.copy(caloriesUiState = CaloriesUiState.Error(it.message.toString()))
            }
        }
    }
}
