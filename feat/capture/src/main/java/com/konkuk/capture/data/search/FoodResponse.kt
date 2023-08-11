package com.konkuk.capture.data.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodsResponse(
    @SerialName("pageNo") val pageNo: Int = 1,
    @SerialName("items") val items: List<FoodResponse> = emptyList(),
)

@Serializable
data class FoodResponse(
    @SerialName("DESC_KOR") val name: String = "",
    @SerialName("NUTR_CONT1") val calories: Double = 0.0,
    @SerialName("NUTR_CONT2") val carbohydrates: Double = 0.0,
    @SerialName("NUTR_CONT3") val protein: Double = 0.0,
    @SerialName("NUTR_CONT4") val fat: Double = 0.0,
    @SerialName("NUTR_CONT5") val sugar: Double = 0.0,
    @SerialName("NUTR_CONT6") val sodium: Double = 0.0,
    @SerialName("NUTR_CONT7") val cholesterol: Double = 0.0,
    @SerialName("NUTR_CONT8") val saturatedFat: Double = 0.0,
    @SerialName("NUTR_CONT9") val transFat: Double = 0.0,
)
