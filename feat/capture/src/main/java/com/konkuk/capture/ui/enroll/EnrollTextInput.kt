package com.konkuk.capture.ui.enroll

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.konkuk.capture.databinding.ActivityEnrollTextInputBinding
import com.konkuk.capture.ui.result.CaptureResultActivity
import com.konkuk.capture.ui.result.CaptureResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnrollTextInput : AppCompatActivity() {

    private lateinit var binding: ActivityEnrollTextInputBinding
    private val viewModel by viewModels<EnrollTextInputViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnrollTextInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vm = viewModel
        binding.lifecycleOwner = this

        initViews()
    }

    private fun initViews() = with(binding) {
        btnBack.setOnClickListener {
            finish()
        }
        btnNext.setOnClickListener {
            finish()
            startActivity(
                Intent(this@EnrollTextInput, CaptureResultActivity::class.java).apply {
                    putExtra(CaptureResultViewModel.FOOD_INFO_KEY, viewModel.makeFoodInfo())
                },
            )
        }
    }
}
