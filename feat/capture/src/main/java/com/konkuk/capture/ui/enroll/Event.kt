package com.konkuk.capture.ui.enroll

sealed class Event {
    data class EnrollEvent(val enrollMethod: EnrollMethod, val name: String) : Event()
}
