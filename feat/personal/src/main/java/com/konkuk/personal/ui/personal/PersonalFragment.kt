package com.konkuk.personal.ui.personal

import android.annotation.SuppressLint
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        observeUiState()
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateCaloriesProgress(uiState.caloriesUiState)
                    updateNutrition(uiState.nutritionUiState)
                    updateWeeklyNutrition(uiState.weeklyCaloriesUiState)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateCaloriesProgress(caloriesUiState: CaloriesUiState) = with(binding) {
        when (caloriesUiState) {
            is CaloriesUiState.Uninitialized -> {} // TODO
            is CaloriesUiState.Error -> {} // TODO
            is CaloriesUiState.InProgress -> {
                // 칼로리 UI 갱신하는 부분
                caloriesTextView.text = "${caloriesUiState.calories} calories"
                txtCalorie.text = "${caloriesUiState.calories} kcal"
                progressBar.progress = caloriesUiState.progress
                txtCalPercent.text = caloriesUiState.progress.toString() + "%"
                /* caloriesUiState.calories
                 caloriesUiState.progress*/
            }
        }
    }

    private fun updateNutrition(nutritionUiState: NutritionUiState) = with(binding) {
        when (nutritionUiState) {
            is NutritionUiState.Uninitialized -> {} // TODO
            is NutritionUiState.Error -> {} // TODO
            is NutritionUiState.Avail -> {
                // 탄단지 UI 갱신하는 부분
                txtCarbohydrate.text = "${nutritionUiState.carbohydrates} g"
                txtProtein.text = "${nutritionUiState.protein} g"
                txtFat.text = "${nutritionUiState.fat} g"
                nutritionUiState.carbohydrates
                nutritionUiState.protein
                nutritionUiState.fat
            }
        }
    }

    private fun updateWeeklyNutrition(weeklyCaloriesUiState: WeeklyCaloriesUiState) {
        when (weeklyCaloriesUiState) {
            is WeeklyCaloriesUiState.Uninitialized -> {} // TODO
            is WeeklyCaloriesUiState.Error -> {} // TODO
            is WeeklyCaloriesUiState.Avail -> {
                // 주간 칼로리 UI 갱신
                weeklyCaloriesUiState.weeklyCaloriesList[6].first // "6/23"
                weeklyCaloriesUiState.weeklyCaloriesList[6].second // 745 (int 칼로리 값)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
