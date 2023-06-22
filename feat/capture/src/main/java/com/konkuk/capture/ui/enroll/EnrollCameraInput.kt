package com.konkuk.capture.ui.enroll

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.konkuk.capture.databinding.ActivityEnrollCameraInputBinding
import com.konkuk.capture.ui.capture.CaptureActivity
import com.konkuk.capture.ui.result.CaptureResultActivity

class EnrollCameraInput : AppCompatActivity() {

    private lateinit var binding: ActivityEnrollCameraInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnrollCameraInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뒤로가기
        initViews()
    }

    private fun initViews() = with(binding) {
        btnNext.setOnClickListener {
            finish()
            startActivity(
                Intent(this@EnrollCameraInput, CaptureActivity::class.java).apply {
                    putExtra(CaptureResultActivity.NAME_KEY, etName.text.toString())
                },
            )
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
