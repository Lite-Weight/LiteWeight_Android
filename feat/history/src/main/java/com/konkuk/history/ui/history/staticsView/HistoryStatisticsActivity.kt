package com.konkuk.history.ui.history.staticsView

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.konkuk.history.databinding.ActivityHistoryStatisticsBinding
import com.konkuk.history.ui.history.HistoryAdapter
import com.konkuk.history.ui.history.HistoryViewModel

class HistoryStatisticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryStatisticsBinding

    private lateinit var staticsAdapter: HistoryAdapter

    private val viewModel by viewModels<HistoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHistoryStatisticsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initHistoryRecyclerView()
    }

    private fun initHistoryRecyclerView() = with(binding) {
        rvHistoryStatistics.layoutManager = LinearLayoutManager(
            this@HistoryStatisticsActivity,
            LinearLayoutManager.VERTICAL,
            false,
        )
        staticsAdapter = HistoryAdapter { historyItemModel ->
            viewModel.getFoodInfo(historyItemModel.id) { foodInfo ->
            }
        }
        rvHistoryStatistics.adapter = staticsAdapter
    }
}
