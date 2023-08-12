package com.konkuk.capture.data.search

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.konkuk.common.data.FoodInfo
import kotlinx.serialization.Serializable

@Serializable
data class FoodsResponse(
    val header: Header,
    val body: FoodsResponseBody,
)

@Serializable
data class Header(
    val resultCode: String,
    val resultMsg: String,
)

@Serializable
data class FoodsResponseBody(
    val pageNo: Int,
    val totalCount: Int,
    val numOfRows: Int,
    val items: List<FoodResponse>,
)

@Serializable
data class FoodResponse(
    @SerializedName("DESC_KOR") val name: String?,
    @SerializedName("SERVING_WT") val weight: String?,
    @SerializedName("NUTR_CONT1") val calories: String?,
    @SerializedName("NUTR_CONT2") val carbohydrates: String?,
    @SerializedName("NUTR_CONT3") val protein: String?,
    @SerializedName("NUTR_CONT4") val fat: String?,
    @SerializedName("NUTR_CONT5") val sugar: String?,
    @SerializedName("NUTR_CONT6") val sodium: String?,
    @SerializedName("NUTR_CONT7") val cholesterol: String?,
    @SerializedName("NUTR_CONT8") val saturatedFat: String?,
    @SerializedName("NUTR_CONT9") val transFat: String?,
    @SerializedName("BGN_YEAR") val BGN_YEAR: String?,
    @SerializedName("ANIMAL_PLANT") val ANIMAL_PLANT: String?,
)

fun FoodResponse.toFoodInfo() = FoodInfo(
    0L,
    name ?: "",
    System.currentTimeMillis(),
    carbohydrates?.toFloatOrNull() ?: 0f,
    sodium?.toFloatOrNull() ?: 0f,
    sugar?.toFloatOrNull() ?: 0f,
    cholesterol?.toFloatOrNull() ?: 0f,
    transFat?.toFloatOrNull() ?: 0f,
    saturatedFat?.toFloatOrNull() ?: 0f,
    protein?.toFloatOrNull() ?: 0f,
    fat?.toFloatOrNull() ?: 0f,
    calories?.toFloatOrNull() ?: 0f,
    weight?.toIntOrNull() ?: 0,
).apply { Log.d("FoodResponse", this.toString()) }
