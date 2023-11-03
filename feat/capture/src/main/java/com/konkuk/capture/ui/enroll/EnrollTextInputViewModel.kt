package com.konkuk.capture.ui.enroll

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.konkuk.common.data.FoodInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class EnrollTextInputViewModel @Inject constructor(
    savesStateHandle: SavedStateHandle,
) : ViewModel() {
    private val sodiumRegex = Regex("나트[륨룸]([\\d.]+)\\D")
    private val carbohydratesRegex = Regex("탄수화물([\\d.]+)\\D")
    private val fatRegex = Regex("지방([\\d.]+)\\D")
    private val cholesterolRegex = Regex("콜[레래러]스[태테]롤([\\d.]+)\\D")
    private val proteinRegex = Regex("단백질([\\d.]+)\\D")
    private val sugarRegex = Regex("당[류루]([\\d.]+)\\D")
    private val transFatRegex = Regex("트[랜렌]스지방([\\d.]+)\\D")
    private val saturatedFatRegex = Regex("포화지방([\\d.]+)\\D")
    private val totalGramsRegex = Regex("총내용[량랑](\\d+)g")
    private val totalCaloriesRegex = Regex("(\\d+)kca")
    private val perCaloriesRegex = Regex("(\\d+)g당(\\d+)kca")

    val name = MutableStateFlow("")
    val sodium = MutableStateFlow("")
    val carbohydrates = MutableStateFlow("")
    val fat = MutableStateFlow("")
    val cholesterol = MutableStateFlow("")
    val protein = MutableStateFlow("")
    val sugar = MutableStateFlow("")
    val transFat = MutableStateFlow("")
    val saturatedFat = MutableStateFlow("")
    val calories = MutableStateFlow("")

    val capturedPicture = MutableStateFlow<CapturedPicture>(CapturedPicture.None)

    init {
        savesStateHandle.get<String>(OCR_RESULT_KEY)?.let { text ->
            val result = text.replace(" ", "")
                .replace(",", ".")
                .replace(")", "")

            setFoodInfo(result)
        }
        savesStateHandle.get<FoodInfo?>(API_RESULT_KEY)?.let {
            setFoodInfo(it)
        }
        savesStateHandle.get<Uri>(URI_PICTURE_KEY)?.let {
            capturedPicture.value = CapturedPicture.UriPicture(it)
        } ?: savesStateHandle.get<Bitmap>(BITMAP_PICTURE_KEY)?.let {
            capturedPicture.value = CapturedPicture.BitmapPicture(it)
        }
    }

    private fun setFoodInfo(foodInfo: FoodInfo) {
        name.value = foodInfo.name
        sodium.value = foodInfo.sodium.toString()
        carbohydrates.value = foodInfo.carbohydrates.toString()
        sugar.value = foodInfo.sugar.toString()
        cholesterol.value = foodInfo.cholesterol.toString()
        fat.value = foodInfo.fat.toString()
        protein.value = foodInfo.protein.toString()
        transFat.value = foodInfo.transFat.toString()
        saturatedFat.value = foodInfo.saturatedFat.toString()
        calories.value = foodInfo.calories.toString()
    }

    private fun setFoodInfo(result: String) {
        val sodiumResult = sodiumRegex.find(result)
        val carbohydratesResult = carbohydratesRegex.find(result)
        val fatResult = fatRegex.find(result)
        val proteinResult = proteinRegex.find(result)
        val sugarResult = sugarRegex.find(result)
        val cholesterolResult = cholesterolRegex.find(result)
        val transFatResult = transFatRegex.find(result)
        val saturatedFatResult = saturatedFatRegex.find(result)
        val totalGramsResult = totalGramsRegex.find(result)
        val totalCaloriesResult = totalCaloriesRegex.find(result)
        val perCaloriesResult = perCaloriesRegex.find(result)

        sodium.value = if (sodiumResult != null) sodiumResult.groupValues[1] else ""
        carbohydrates.value =
            if (carbohydratesResult != null) carbohydratesResult.groupValues[1] else ""
        sugar.value = if (sugarResult != null) sugarResult.groupValues[1] else ""
        cholesterol.value =
            if (cholesterolResult != null) cholesterolResult.groupValues[1] else ""
        fat.value = if (fatResult != null) fatResult.groupValues[1] else ""
        protein.value = if (proteinResult != null) proteinResult.groupValues[1] else ""
        transFat.value =
            if (transFatResult != null) transFatResult.groupValues[1] else ""
        saturatedFat.value =
            if (saturatedFatResult != null) saturatedFatResult.groupValues[1] else ""
        calories.value = if (totalGramsResult != null && perCaloriesResult != null) {
            ((totalGramsResult.groupValues[1].toFloat() * perCaloriesResult.groupValues[2].toFloat()) / perCaloriesResult.groupValues[1].toFloat()).toString()
        } else if (totalCaloriesResult != null) {
            totalCaloriesResult.groupValues[1]
        } else {
            ""
        }
    }

    fun makeFoodInfo() = FoodInfo(
        0L,
        name.value,
        System.currentTimeMillis(),
        carbohydrates.value.toFloatOrNull() ?: 0f,
        sodium.value.toFloatOrNull() ?: 0f,
        sugar.value.toFloatOrNull() ?: 0f,
        cholesterol.value.toFloatOrNull() ?: 0f,
        transFat.value.toFloatOrNull() ?: 0f,
        saturatedFat.value.toFloatOrNull() ?: 0f,
        protein.value.toFloatOrNull() ?: 0f,
        fat.value.toFloatOrNull() ?: 0f,
        calories.value.toFloatOrNull() ?: 0f,
    )

    companion object {
        const val OCR_RESULT_KEY = "OCR_RESULT_KEY"
        const val API_RESULT_KEY = "API_RESULT_KEY"
        const val BITMAP_PICTURE_KEY = "BITMAP_PICTURE_KEY"
        const val URI_PICTURE_KEY = "URI_PICTURE_KEY"
    }
}

sealed class CapturedPicture {
    data class UriPicture(val uri: Uri) : CapturedPicture()
    data class BitmapPicture(val bitmap: Bitmap) : CapturedPicture()
    object None : CapturedPicture()
}
