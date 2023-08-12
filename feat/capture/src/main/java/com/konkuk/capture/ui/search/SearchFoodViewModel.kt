package com.konkuk.capture.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.konkuk.capture.domain.SearchFoodNameUseCase
import com.konkuk.common.data.FoodInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFoodViewModel @Inject constructor(
    private val searchFoodNameUseCase: SearchFoodNameUseCase,
) : ViewModel() {
    private val _foodList: MutableStateFlow<Flow<PagingData<FoodInfo>>?> = MutableStateFlow(null)
    val foodList get() = _foodList.asStateFlow()

    private val _event = MutableSharedFlow<FoodInfo?>()
    val event get() = _event.asSharedFlow()

    val textField = MutableStateFlow("")

    init {
        viewModelScope.launch {
            textField.debounce(300).collectLatest {
                _foodList.value = searchFoodNameUseCase(it)
            }
        }
    }

    fun removeText() {
        textField.value = ""
    }

    fun enroll() {
        viewModelScope.launch {
            _event.emit(null)
        }
    }
}
