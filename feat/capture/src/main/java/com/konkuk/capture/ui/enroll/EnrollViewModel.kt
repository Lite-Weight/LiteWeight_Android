package com.konkuk.capture.ui.enroll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnrollViewModel @Inject constructor() : ViewModel() {
    private val _enrollUiState = MutableStateFlow(EnrollUiState())
    val enrollUiState get() = _enrollUiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event get() = _event.asSharedFlow()

    fun selectPicture() {
        _enrollUiState.value = _enrollUiState.value.copy(enrollMethod = EnrollMethod.PICTURE)
    }

    fun selectWriting() {
        _enrollUiState.value = _enrollUiState.value.copy(enrollMethod = EnrollMethod.WRITING)
    }

    fun textChanged(text: CharSequence) {
        _enrollUiState.value = _enrollUiState.value.copy(enrollName = text.toString())
    }

    fun onEnrollClick() {
        viewModelScope.launch {
            _event.emit(
                Event.EnrollEvent(
                    _enrollUiState.value.enrollMethod,
                    _enrollUiState.value.enrollName,
                ),
            )
        }
    }
}
