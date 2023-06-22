package com.konkuk.capture.ui.result

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.konkuk.capture.databinding.ActivityCaptureResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CaptureResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCaptureResultBinding
    private val viewModel by viewModels<CaptureResultViewModel>()

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
        binding.tvResult.text = viewModel.name.value
    }
}
