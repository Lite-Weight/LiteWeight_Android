package com.konkuk.capture.ui.enroll

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.konkuk.capture.databinding.ActivityEnrollCameraInputBinding

class EnrollCameraInput : AppCompatActivity() {

    private lateinit var binding: ActivityEnrollCameraInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnrollCameraInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
