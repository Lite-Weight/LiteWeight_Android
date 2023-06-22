package com.konkuk.common.Extension

import java.text.SimpleDateFormat

fun Long.toDate(format: String): String {
    val sdf = SimpleDateFormat(format, java.util.Locale.KOREA)
    return sdf.format(this)
}
