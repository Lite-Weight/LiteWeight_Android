package com.konkuk.history.ui.history.staticsView

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.konkuk.history.databinding.ActivityHistoryStatisticsBinding
import com.konkuk.history.ui.history.HistoryViewModel

class HistoryStatisticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryStatisticsBinding

    private val viewModel by viewModels<HistoryViewModel>()

    private var dataList = ArrayList<StaticsItemModel>()

    private lateinit var adapter: StaticsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHistoryStatisticsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initData()
        initRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initData() {
        dataList.add(StaticsItemModel("칼로리", 2500, 1500, "12-18", "kcal"))
        dataList.add(StaticsItemModel("단백질", 2000, 500, "12-18", "g"))
        dataList.add(StaticsItemModel("지방", 2000, 500, "12-18", "g"))
        dataList.add(StaticsItemModel("지방", 2000, 500, "12-18", "g"))
        dataList.add(StaticsItemModel("지방", 2000, 500, "12-18", "g"))
        dataList.add(StaticsItemModel("지방", 2000, 500, "12-18", "g"))

        Log.d("datalist", dataList[5].itemName)
    }

    /*    private fun initHistoryRecyclerView() = with(binding) {
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
        }*/

    private fun initRecyclerView() = with(binding) {
        // 기존 adapter(recyclerview adpater)
        rvHistoryStatistics.layoutManager = LinearLayoutManager(
            this@HistoryStatisticsActivity,
            LinearLayoutManager.VERTICAL,
            false,
        )
        adapter = StaticsAdapter(dataList)
        rvHistoryStatistics.adapter = adapter
    }
}
