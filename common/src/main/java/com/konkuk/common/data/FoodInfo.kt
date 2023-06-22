package com.konkuk.common.data

import androidx.room.Entity

@Entity
data class FoodInfo(
    val name: String,
    val date: Long,
    val carbohydrates: Float,
    val sodium: Float,
    val sugar: Float,
    val cholesterol: Float,
    val transFat: Float,
    val saturatedFat: Float,
    val protein: Float,
    val fat: Float,
    val calories: Float,
)
