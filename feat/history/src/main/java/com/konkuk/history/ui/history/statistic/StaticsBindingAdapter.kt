package com.konkuk.history.ui.history.statistic

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.konkuk.history.data.datasource.statistic.NutritionStat

@BindingAdapter("gender")
fun TextView.gender(gender: GENDER) {
    this.text = if (gender == GENDER.NONE) "" else if (gender == GENDER.MALE) "남성" else "여성"
}

@BindingAdapter("ageRange")
fun TextView.ageRange(age: Int) {
    if (age != 0) {
        val range = NutritionStat.ageList[NutritionStat.getAgeIndex(age)]
        this.text = "${range.first} ~ ${range.last}살"
    }
}
