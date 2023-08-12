package com.konkuk.history.domain.usecase

import java.util.Calendar
import javax.inject.Inject

class GetMonthUseCase @Inject constructor() {
    operator fun invoke(): Int {
        return Calendar.getInstance().get(Calendar.MONTH) + 1
    }
}
