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
import com.konkuk.history.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModels<HistoryViewModel>()

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
                historyListTextView.text = historyListUiState.list.toString()
            }
        }
    }
}
