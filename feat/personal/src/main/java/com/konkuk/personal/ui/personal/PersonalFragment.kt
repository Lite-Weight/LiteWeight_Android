package com.konkuk.personal.ui.personal

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
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
                /* if (caloriesUiState.calories == null) {
                     txtCalorie.text = "0 kcal"
                     progressBar.progress = 0
                     txtCalPercent.text = "0%"
                 } else {*/
                // 칼로리 UI 갱신하는 부분
                txtCalorie.text = "${caloriesUiState.calories} kcal"
                progressBar.progress = caloriesUiState.progress
                Log.d("progress", caloriesUiState.progress.toString())
                txtCalPercent.text = caloriesUiState.progress.toString() + "%"
                /* caloriesUiState.calories
                 caloriesUiState.progress*/
//                }
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

    private fun updateWeeklyNutrition(weeklyCaloriesUiState: WeeklyCaloriesUiState) =
        with(binding) {
            when (weeklyCaloriesUiState) {
                is WeeklyCaloriesUiState.Uninitialized -> {} // TODO
                is WeeklyCaloriesUiState.Error -> {} // TODO
                is WeeklyCaloriesUiState.Avail -> {
                    // 주간 칼로리 UI 갱신
                    weeklyCaloriesUiState.weeklyCaloriesList[6].first // "6/23"
                    weeklyCaloriesUiState.weeklyCaloriesList[6].second // 745 (int 칼로리 값)

                    val entries = ArrayList<BarEntry>()

                    for (i in 0..6) {
                        Log.d(
                            "weeklyCaloriesList",
                            weeklyCaloriesUiState.weeklyCaloriesList[i].first.toString(),
                        )
                    }

                    // 그래프 역순 정렬
                    for (i in 6 downTo 0) {
                        entries.add(
                            BarEntry(
                                weeklyCaloriesUiState.weeklyCaloriesList[i].first.toFloat(),
                                weeklyCaloriesUiState.weeklyCaloriesList[i].second.toFloat(),
                            ),
                        )
                    }
                    // 그래프 데이터 추가
                    initBarChart(entries)
                }
            }
        }

    private fun initBarChart(entries: ArrayList<BarEntry>) = with(binding) {
        val barDataSet = BarDataSet(entries, "일간 칼로리")
        barDataSet.color =
            ContextCompat.getColor(requireContext(), com.konkuk.common.R.color.main_blue)

        barDataSet.valueTextColor = Color.BLACK

        val barData = BarData(barDataSet)

        barChart.data = barData
        barChart.setDrawBorders(false)
        barChart.setDrawGridBackground(false)

        /* val description = Description()
         description.text = "주간 칼로리 그래프"*/
        barChart.description.isEnabled = false

        val legend = barChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.form = Legend.LegendForm.CIRCLE
        legend.formSize = 11f
        legend.textColor = Color.BLACK

        val xAxis = barChart.xAxis
        xAxis.valueFormatter = GraphAxisValueFormatter()
        /*  xAxis.valueFormatter = object : ValueFormatter() {
              override fun getFormattedValue(value: Float): String {
                  return "${value.toInt()}일"
              }
          }*/
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.BLACK
        xAxis.textSize = 11f
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        val yAxis = barChart.axisLeft
        yAxis.textColor = Color.BLACK
        yAxis.textSize = 11f
        yAxis.setDrawGridLines(false)
        yAxis.setDrawAxisLine(false)

        barChart.axisRight.isEnabled = false
        barChart.animateY(1000)
        barChart.invalidate()
    }

    // 그래프 데이터 추가 및 그리기
    private fun initChartDataSet(entries: ArrayList<Entry>) = with(binding) {
        for (i in 0..6) {
            Log.d("entries", entries[i].toString())
        }

        val lineDataSet = LineDataSet(entries, "주간 칼로리")
        lineDataSet.color = Color.BLUE
        lineDataSet.valueTextColor = Color.BLACK

        val lineData = LineData(lineDataSet)

        lineChart.data = lineData
        lineChart.setDrawBorders(false)
        lineChart.setDrawGridBackground(false)

        val description = Description()
        description.text = "주간 칼로리 그래프"
        lineChart.description = description

        val legend = lineChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.form = Legend.LegendForm.CIRCLE
        legend.formSize = 10f
        legend.textSize = 13f
        legend.textColor = Color.parseColor("#A3A3A3")
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.yEntrySpace = 5f
        legend.isWordWrapEnabled = true
        legend.xOffset = 80f
        legend.yOffset = 20f
        legend.calculatedLineSizes

        // XAxis (아래쪽) - 선 유무, 사이즈, 색상, 축 위치 설정

        // XAxis (아래쪽) - 선 유무, 사이즈, 색상, 축 위치 설정
        val xAxis = lineChart.xAxis
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM // x축 데이터 표시 위치

        xAxis.granularity = 1f
        xAxis.textSize = 14f
        xAxis.textColor = Color.rgb(118, 118, 118)
        xAxis.spaceMin = 0.1f // Chart 맨 왼쪽 간격 띄우기

        xAxis.spaceMax = 0.1f // Chart 맨 오른쪽 간격 띄우기

        // YAxis(Right) (왼쪽) - 선 유무, 데이터 최솟값/최댓값, 색상

        // YAxis(Right) (왼쪽) - 선 유무, 데이터 최솟값/최댓값, 색상
        val yAxisLeft = lineChart.axisLeft
        yAxisLeft.textSize = 14f
        yAxisLeft.textColor = Color.rgb(163, 163, 163)
        yAxisLeft.setDrawAxisLine(false)
        yAxisLeft.axisLineWidth = 2f
        yAxisLeft.axisMinimum = 0f // 최솟값

        /*  yAxisLeft.axisMaximum = RANGE.get(0).get(range) // 최댓값

          yAxisLeft.granularity = RANGE.get(1).get(range)*/

        // YAxis(Left) (오른쪽) - 선 유무, 데이터 최솟값/최댓값, 색상

        // YAxis(Left) (오른쪽) - 선 유무, 데이터 최솟값/최댓값, 색상
        val yAxis = lineChart.axisRight
        yAxis.setDrawLabels(false) // label 삭제

        yAxis.textColor = Color.rgb(163, 163, 163)
        yAxis.setDrawAxisLine(false)
        yAxis.axisLineWidth = 2f
        yAxis.axisMinimum = 0f // 최솟값

        /* yAxis.axisMaximum = RANGE.get(0).get(range) // 최댓값

         yAxis.granularity = RANGE.get(1).get(range)*/

        lineChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
