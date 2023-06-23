package com.konkuk.capture.ui.enroll

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.konkuk.capture.databinding.ActivityEnrollTextInputBinding

class EnrollTextInput : AppCompatActivity() {

    private lateinit var binding: ActivityEnrollTextInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnrollTextInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() = with(binding) {
        btnBack.setOnClickListener {
            finish()
        }
    }
}
