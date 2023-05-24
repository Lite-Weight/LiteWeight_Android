package com.konkuk.capture.ui.enroll

data class EnrollUiState(
    val enrollMethod: EnrollMethod = EnrollMethod.UNSELECTED,
    val enrollName: String = "",
)

enum class EnrollMethod {
    UNSELECTED, PICTURE, WRITING
}
