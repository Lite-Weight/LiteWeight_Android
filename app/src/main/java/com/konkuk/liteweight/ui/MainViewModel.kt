package com.konkuk.liteweight.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private var _event = MutableSharedFlow<Event>()
    val event get() = _event.asSharedFlow()

    fun onCaptureClick() {
        viewModelScope.launch {
            _event.emit(Event.CaptureClickEvent)
        }
    }
}
