package com.konkuk.history.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.konkuk.common.Extension.toDate
import com.konkuk.history.databinding.FragmentHistoryBinding
import com.konkuk.history.domain.model.HistoryCalendarModel
import com.konkuk.history.domain.model.HistoryItemModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModels<HistoryViewModel>()

    private lateinit var calendarAdapter: CalendarMainAdapter

    private lateinit var historyAdapter: HistoryAdapter

    private var dataList = ArrayList<HistoryCalendarModel>()

    private var historyDataList = ArrayList<HistoryItemModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        initViews()
        observeUiState()
        initRecyclerView()
        initData()
        initHistoryRecyclerView()
    }

    private fun initHistoryRecyclerView() = with(binding) {
        HistoryRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false,
        )
        historyAdapter = HistoryAdapter() { data ->
            viewModel.selectDay(data.date.toDate("dd").toInt())
        }
        HistoryRecyclerView.adapter = historyAdapter
    }

    /*    private fun initData() {
            val calendar = Calendar.getInstance()
            // 이번 달의 말일
            val totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
            val dayFormat = SimpleDateFormat("E", Locale.getDefault())

            for (i in 0 until totalDays) {
                val date = dateFormat.format(calendar.time)
                val dayOfWeek = dayFormat.format(calendar.time)

                dataList.add(HistoryCalendarModel(date, dayOfWeek))

                if (date == totalDays.toString()) {
                    break
                }
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
        }*/

    private fun initData() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1) // 이번 달의 1일로 설정

        val totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
        val dayFormat = SimpleDateFormat("E", Locale.getDefault())

        for (i in 1..totalDays) {
            val date = dateFormat.format(calendar.time)
            val dayOfWeek = dayFormat.format(calendar.time)

            dataList.add(HistoryCalendarModel(date, dayOfWeek))

            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    private fun initRecyclerView() = with(binding) {
        CalendarRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false,
        )
        calendarAdapter = CalendarMainAdapter(dataList) { data ->
//            binding.
        }
        CalendarRecyclerView.adapter = calendarAdapter
    }

    private fun initViews() = with(binding) {
        selectButton1.setOnClickListener { viewModel.selectDay(1) }
        selectButton2.setOnClickListener { viewModel.selectDay(2) }
        selectButton3.setOnClickListener { viewModel.selectDay(3) }
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateHistoryDate(uiState.historyDateUiState)
                    updateHistoryList(uiState.historyListUiState)
                }
            }
        }
    }

    private fun updateHistoryDate(historyDateUiState: HistoryDateUiState) = with(binding) {
        when (historyDateUiState) {
            is HistoryDateUiState.Uninitialized -> {}
            is HistoryDateUiState.Error -> {
                todayTextView.text = historyDateUiState.message
            }

            is HistoryDateUiState.Avail -> {
                todayTextView.text = historyDateUiState.today.toString()
                selectedDayTextView.text = historyDateUiState.selectedDay.toString()
            }
        }
    }

    private fun updateHistoryList(historyListUiState: HistoryListUiState) = with(binding) {
        when (historyListUiState) {
            is HistoryListUiState.Uninitialized -> {}
            is HistoryListUiState.Error -> {
                historyListTextView.text = historyListUiState.message
            }

            is HistoryListUiState.Avail -> {
                historyAdapter.submitList(historyListUiState.list)
            }
        }
    }
}
