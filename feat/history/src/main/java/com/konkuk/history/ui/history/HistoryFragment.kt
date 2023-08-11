package com.konkuk.history.ui.history

import android.content.Intent
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
import com.konkuk.capture.ui.result.CaptureResultActivity
import com.konkuk.capture.ui.result.CaptureResultViewModel
import com.konkuk.common.ui.decoration.FirstItemDecoration
import com.konkuk.history.databinding.FragmentHistoryBinding
import com.konkuk.history.ui.history.staticsView.HistoryStatisticsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModels<HistoryViewModel>()

    private lateinit var calendarAdapter: CalendarMainAdapter

    private lateinit var historyAdapter: HistoryAdapter

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
        initHistoryRecyclerView()
        NextBtn()
    }

    private fun NextBtn() {
        val intent = Intent(requireContext(), HistoryStatisticsActivity::class.java)
        startActivity(intent)
    }

    private fun initHistoryRecyclerView() = with(binding) {
        historyRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false,
        )
        historyAdapter = HistoryAdapter { historyItemModel ->
            viewModel.getFoodInfo(historyItemModel.id) { foodInfo ->
                startActivity(
                    Intent(requireContext(), CaptureResultActivity::class.java).apply {
                        putExtra(
                            CaptureResultViewModel.FOOD_INFO_KEY,
                            foodInfo,
                        )
                    },
                )
            }
        }
        historyRecyclerView.adapter = historyAdapter
    }

    private fun initRecyclerView() = with(binding) {
        calendarRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false,
        ).also {
            it.scrollToPosition(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1)
        }
        calendarAdapter = CalendarMainAdapter { data ->
            viewModel.selectDay(data.date.toInt())
        }
        calendarRecyclerView.adapter = calendarAdapter
        calendarRecyclerView.addItemDecoration(FirstItemDecoration())
    }

    private fun initViews() = with(binding) {
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
            is HistoryDateUiState.Error -> {}
            is HistoryDateUiState.Avail -> {
                calendarAdapter.submitList(historyDateUiState.calendarList.toList())
            }
        }
    }

    private fun updateHistoryList(historyListUiState: HistoryListUiState) = with(binding) {
        when (historyListUiState) {
            is HistoryListUiState.Uninitialized -> {}
            is HistoryListUiState.Error -> {}
            is HistoryListUiState.Avail -> {
                if (historyListUiState.list.isEmpty()) {
                    historyRecyclerView.visibility = View.GONE
                    sorrydog.visibility = View.VISIBLE
                } else {
                    historyRecyclerView.visibility = View.VISIBLE
                    sorrydog.visibility = View.GONE
                    historyAdapter.submitList(historyListUiState.list)
                }
            }
        }
    }
}
