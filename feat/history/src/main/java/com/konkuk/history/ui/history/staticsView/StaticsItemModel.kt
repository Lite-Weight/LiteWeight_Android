package com.konkuk.history.ui.history.staticsView

import java.io.Serializable

data class StaticsItemModel(
    val itemName: String,
    val avgCalorie: Int,
    val myCalorie: Int,
    val avgAge: String,
    val foodUnit: String,
) : Serializable
