package com.konkuk.personal.ui.personal

import com.github.mikephil.charting.formatter.ValueFormatter

class GraphAxisValueFormatter() : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        return "${value.toInt()}일"
    }
}
