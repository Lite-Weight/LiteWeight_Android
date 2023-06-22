package com.konkuk.capture.ui.result

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.konkuk.capture.databinding.ActivityCaptureResultBinding
import com.konkuk.common.data.FoodInfo

class CaptureResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCaptureResultBinding

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCaptureResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        val result = intent.getStringExtra(OCR_RESULT_KEY).toString()
            .replace(" ", "")
            .replace("\n", "")
            .replace(",", ".")
            .replace(")", "")

        val foodInfo = makeFoodInfo(intent.getStringExtra(NAME_KEY).toString(), result)

        binding.tvResult.text = foodInfo.toString()
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
