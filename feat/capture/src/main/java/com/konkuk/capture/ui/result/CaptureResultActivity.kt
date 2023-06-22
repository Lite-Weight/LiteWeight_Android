package com.konkuk.capture.ui.result

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.konkuk.capture.databinding.ActivityCaptureResultBinding

class CaptureResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCaptureResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCaptureResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        binding.tvResult.text =
            intent.getStringExtra(NAME_KEY) + "\n" + intent.getStringExtra(OCR_RESULT_KEY)
    }

    companion object {
        const val NAME_KEY = "NAME_KEY"
        const val OCR_RESULT_KEY = "OCR_RESULT_KEY"
    }
}
