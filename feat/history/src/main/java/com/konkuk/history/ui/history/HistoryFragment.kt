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
import com.konkuk.history.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Date

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
    }

    private fun initHistoryRecyclerView() = with(binding) {
        historyRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false,
        )
        historyAdapter = HistoryAdapter { data ->
        }
        historyRecyclerView.adapter = historyAdapter
    }

    private fun initRecyclerView() = with(binding) {
        calendarRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false,
        ).also {
            it.scrollToPosition(Date(System.currentTimeMillis()).date - 1)
        }
        calendarAdapter = CalendarMainAdapter { data ->
            viewModel.selectDay(data.date.toInt())
        }
        calendarRecyclerView.adapter = calendarAdapter
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
                historyAdapter.submitList(historyListUiState.list)
            }
        }
    }
}
