package com.konkuk.common.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foodInfo")
data class FoodInfo(
    @PrimaryKey(autoGenerate = true) val id: Long,
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
