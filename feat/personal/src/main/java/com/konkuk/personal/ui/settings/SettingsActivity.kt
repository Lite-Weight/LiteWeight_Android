package com.konkuk.personal.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.konkuk.personal.databinding.ActivitySettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        initViews()
    }

    private fun initViews() = with(binding) {
        llName.setOnClickListener {
            startActivity(Intent(this@SettingsActivity, EditNameActivity::class.java))
        }

        llAge.setOnClickListener {
            startActivity(Intent(this@SettingsActivity, EditAgeActivity::class.java))
        }

        llGender.setOnClickListener {
            val bottomSheetDialogFragment = EditGenderBottomSheetDialogFragment()
            bottomSheetDialogFragment.show(
                supportFragmentManager,
                bottomSheetDialogFragment.tag,
            )
        }

        ivBackBtn.setOnClickListener {
            finish()
        }
    }
}
