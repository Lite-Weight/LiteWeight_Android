package com.konkuk.personal.ui.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.konkuk.personal.databinding.FragmentPersonalBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonalFragment : Fragment() {
    private var _binding: FragmentPersonalBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PersonalViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPersonalBinding.inflate(inflater, container, false)

        observeUiState()

        return binding.root
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateCaloriesProgress(uiState.caloriesUiState)
                }
            }
        }
    }

    private fun updateCaloriesProgress(caloriesUiState: CaloriesUiState) {
        when (caloriesUiState) {
            is CaloriesUiState.Uninitialized -> {} // TODO
            is CaloriesUiState.Error -> {} // TODO
            is CaloriesUiState.InProgress -> {
                binding.caloriesTextView.text = "${caloriesUiState.progress} calories"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
