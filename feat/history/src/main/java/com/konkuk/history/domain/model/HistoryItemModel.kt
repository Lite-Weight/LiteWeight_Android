package com.konkuk.history.domain.model

import com.konkuk.common.data.FoodInfo

data class HistoryItemModel(
    val id: Long,
    val date: Long,
    val name: String,
    val calories: Int,
    val protein: Int = 0,
    val carbohydrates: Int = 0,
    val fat: Int = 0,
    val sugar: Int = 0,
    val sodium: Int = 0,
)

fun FoodInfo.toHistoryItemModel(): HistoryItemModel {
    return HistoryItemModel(
        id,
        date,
        name,
        calories.toInt(),
        protein.toInt(),
        carbohydrates.toInt(),
        fat.toInt(),
        sugar.toInt(),
        sodium.toInt(),
    )
}
