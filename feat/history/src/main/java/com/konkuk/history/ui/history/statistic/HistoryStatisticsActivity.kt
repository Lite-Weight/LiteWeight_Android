package com.konkuk.history.ui.history.statistic

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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

        initRecyclerView()
        initData()
        initBack()
    }

    private fun initBack() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initData() {
        lifecycleScope.launch {
            viewModel.dataList.collectLatest {
                adapter.submitList(it)
            }
        }
    }

    private fun initRecyclerView() = with(binding) {
        rvHistoryStatistics.layoutManager = LinearLayoutManager(
            this@HistoryStatisticsActivity,
            LinearLayoutManager.VERTICAL,
            false,
        )
        adapter = StaticsAdapter()
        rvHistoryStatistics.adapter = adapter
    }
}
