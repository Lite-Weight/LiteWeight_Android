package com.konkuk.history.ui.history.statistic

import java.io.Serializable

data class StaticsItemModel(
    val itemName: String,
    val avgCalorie: Int,
    val myCalorie: Int,
    val avgAge: String,
    val foodUnit: String,
    val gender: GENDER = GENDER.MALE,
) : Serializable
