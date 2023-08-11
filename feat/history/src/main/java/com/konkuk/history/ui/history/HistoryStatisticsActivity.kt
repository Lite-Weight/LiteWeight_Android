package com.konkuk.history.ui.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.konkuk.history.databinding.ActivityHistoryStatisticsBinding

class HistoryStatisticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryStatisticsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHistoryStatisticsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
