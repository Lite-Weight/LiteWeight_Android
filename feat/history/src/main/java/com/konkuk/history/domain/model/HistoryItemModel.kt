package com.konkuk.history.domain.model

import com.konkuk.common.data.FoodInfo

data class HistoryItemModel(
    val date: Long,
    val name: String,
    val calories: Int,
)

fun FoodInfo.toHistoryItemModel(): HistoryItemModel {
    return HistoryItemModel(date, name, calories.toInt())
}
