package com.konkuk.personal.ui.settings

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.konkuk.personal.databinding.ActivityEditNameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditNameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNameBinding
    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        initViews()
    }

    private fun initViews() = with(binding) {
        ivBackBtn.setOnClickListener {
            finish()
        }

        tvSave.setOnClickListener {
            viewModel.setName()
            finish()
        }
    }
}
