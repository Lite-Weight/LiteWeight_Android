package com.konkuk.history.ui.history.statistic

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.konkuk.history.databinding.ActivityHistoryStatisticsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryStatisticsActivity : AppCompatActivity() {

    private val viewModel by viewModels<StatisticViewModel>()

    private lateinit var binding: ActivityHistoryStatisticsBinding

    private lateinit var adapter: StaticsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHistoryStatisticsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() = with(binding) {
        binding.lifecycleOwner = this@HistoryStatisticsActivity
        binding.vm = viewModel

        adapter = StaticsAdapter()
        rvHistoryStatistics.adapter = adapter

        lifecycleScope.launch {
            viewModel.dataList.collectLatest {
                adapter.submitList(it)
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
