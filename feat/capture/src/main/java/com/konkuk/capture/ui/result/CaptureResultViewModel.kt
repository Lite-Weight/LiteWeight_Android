package com.konkuk.capture.ui.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.capture.domain.SaveFoodHistoryUseCase
import com.konkuk.common.data.FoodInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaptureResultViewModel @Inject constructor(
    private val saveFoodHistoryUseCase: SaveFoodHistoryUseCase,
    savesStateHandle: SavedStateHandle,
) : ViewModel() {
    private val sodiumRegex = Regex("나트륨([\\d.]+)\\D")
    private val carbohydratesRegex = Regex("탄수화물([\\d.]+)\\D")
    private val fatRegex = Regex("지방([\\d.]+)\\D")
    private val cholesterolRegex = Regex("콜[레래러]스[태레]롤([\\d.]+)\\D")
    private val proteinRegex = Regex("단백질([\\d.]+)\\D")
    private val sugarRegex = Regex("당류([\\d.]+)\\D")
    private val transFatRegex = Regex("트[랜렌]스지방([\\d.]+)\\D")
    private val saturatedFatRegex = Regex("포화지방([\\d.]+)\\D")
    private val totalGramsRegex = Regex("총내용량(\\d+)g")
    private val totalCaloriesRegex = Regex("(\\d+)kcal")
    private val perCaloriesRegex = Regex("(\\d+)g당(\\d+)kcal")

    val name = MutableStateFlow(savesStateHandle.get<String>(NAME_KEY))
    val sodium = MutableStateFlow(0f)
    val carbohydrates = MutableStateFlow(0f)
    val fat = MutableStateFlow(0f)
    val cholesterol = MutableStateFlow(0f)
    val protein = MutableStateFlow(0f)
    val sugar = MutableStateFlow(0f)
    val transFat = MutableStateFlow(0f)
    val saturatedFat = MutableStateFlow(0f)
    val calories = MutableStateFlow(0f)

    init {
        val result = savesStateHandle.get<String>(OCR_RESULT_KEY).toString()
            .replace(" ", "")
            .replace("\n", "")
            .replace(",", ".")
            .replace(")", "")

        val a = makeFoodInfo(name.value.toString(), result)
//            .let { foodInfo ->
//                sodium.value = foodInfo.sodium
//                carbohydrates.value = foodInfo.carbohydrates
//                fat.value = foodInfo.fat
//                cholesterol.value = foodInfo.cholesterol
//                protein.value = foodInfo.protein
//                sugar.value = foodInfo.sugar
//                transFat.value = foodInfo.transFat
//                saturatedFat.value = foodInfo.saturatedFat
//                calories.value = foodInfo.calories
//            }
        viewModelScope.launch {
            saveFoodHistoryUseCase(a)
        }
    }

    private fun makeFoodInfo(name: String, result: String): FoodInfo {
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

        val date = System.currentTimeMillis()
        val sodium = if (sodiumResult != null) sodiumResult.groupValues[1].toFloat() else 0f
        val carbohydrates =
            if (carbohydratesResult != null) carbohydratesResult.groupValues[1].toFloat() else 0f
        val sugar = if (sugarResult != null) sugarResult.groupValues[1].toFloat() else 0f
        val cholesterol =
            if (cholesterolResult != null) cholesterolResult.groupValues[1].toFloat() else 0f
        val fat = if (fatResult != null) fatResult.groupValues[1].toFloat() else 0f
        val protein = if (proteinResult != null) proteinResult.groupValues[1].toFloat() else 0f
        val transFat = if (transFatResult != null) transFatResult.groupValues[1].toFloat() else 0f
        val saturatedFat =
            if (saturatedFatResult != null) saturatedFatResult.groupValues[1].toFloat() else 0f
        val calories = if (totalGramsResult != null && perCaloriesResult != null) {
            (totalGramsResult.groupValues[1].toFloat() * perCaloriesResult.groupValues[2].toFloat()) / perCaloriesResult.groupValues[1].toFloat()
        } else if (totalCaloriesResult != null) {
            totalCaloriesResult.groupValues[1].toFloat()
        } else {
            0f
        }

        return FoodInfo(
            0,
            name,
            date,
            carbohydrates,
            sodium,
            sugar,
            cholesterol,
            transFat,
            saturatedFat,
            protein,
            fat,
            calories,
        )
    }

    companion object {
        const val NAME_KEY = "NAME_KEY"
        const val OCR_RESULT_KEY = "OCR_RESULT_KEY"
    }
}
