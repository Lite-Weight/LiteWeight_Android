package com.konkuk.capture.ui.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.capture.domain.SaveFoodHistoryUseCase
import com.konkuk.common.data.FoodInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaptureResultViewModel @Inject constructor(
    saveFoodHistoryUseCase: SaveFoodHistoryUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _foodInfo = MutableStateFlow<FoodInfo?>(null)
    val foodInfo get() = _foodInfo.asStateFlow()

    init {
        savedStateHandle.get<FoodInfo>(FOOD_INFO_KEY)?.let { foodInfo ->
            _foodInfo.value = foodInfo
            viewModelScope.launch {
                saveFoodHistoryUseCase(foodInfo)
            }
        }
    }

    companion object {
        const val FOOD_INFO_KEY = "FOOD_INFO_KEY"
    }
}
