package com.konkuk.history.domain.model

import com.konkuk.common.data.FoodInfo

data class HistoryItemModel(
    val id: Long,
    val date: Long,
    val name: String,
    val calories: Int,
)

fun FoodInfo.toHistoryItemModel(): HistoryItemModel {
    return HistoryItemModel(id, date, name, calories.toInt())
}
