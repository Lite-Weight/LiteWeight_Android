package com.konkuk.personal.ui.settings

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("isEnabled")
fun TextView.isEnabled(enable: Boolean) {
    if (enable) {
        setBackgroundColor(resources.getColor(com.konkuk.common.R.color.main_blue))
        this.isEnabled = true
    } else {
        setBackgroundColor(resources.getColor(com.konkuk.common.R.color.static_gray))
        this.isEnabled = false
    }
}
