package com.konkuk.capture.ui.result

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.konkuk.capture.databinding.ActivityCaptureResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CaptureResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCaptureResultBinding
    private val viewModel: CaptureResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCaptureResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vm = viewModel
        binding.lifecycleOwner = this

        initViews()
    }

    private fun initViews() = with(binding) {
    }
}
