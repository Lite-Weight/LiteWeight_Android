package com.konkuk.personal.data.datasource

import com.konkuk.common.Extension.toDate
import com.konkuk.common.data.FoodInfo
import com.konkuk.common.data.FoodInfoDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

class PersonalDataSource @Inject constructor(private val foodInfoDao: FoodInfoDao) {
    fun getCalories(): Result<Flow<Int>> {
        val calendar = Calendar.getInstance()
        val (year, month, day) = listOf(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH),
        )
        return kotlin.runCatching {
            foodInfoDao.getAll().map { foodInfos: List<FoodInfo> ->
                foodInfos.filter {
                    val (y, m, d) = it.date.toDate("YYYY-MM-dd").split('-').map { it.toInt() }
                    year == y && month == m && day == d
                }.map { foodInfo ->
                    foodInfo.calories
                }.sum().toInt()
            }
        }
    }

    fun getNutrition(): Result<Flow<Triple<Float, Float, Float>>> {
        val calendar = Calendar.getInstance()
        val (year, month, day) = listOf(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH),
        )
        return kotlin.runCatching {
            foodInfoDao.getAll().map { foodInfos: List<FoodInfo> ->
                foodInfos.filter {
                    val (y, m, d) = it.date.toDate("YYYY-MM-dd").split('-').map { it.toInt() }
                    year == y && month == m && day == d
                }.map {
                    Triple(it.carbohydrates, it.protein, it.fat)
                }.let { list ->
                    var car = 0f
                    var protein = 0f
                    var fat = 0f
                    list.forEach { (c, p, f) ->
                        car += c
                        protein += p
                        fat += f
                    }
                    Triple(car, protein, fat)
                }
            }
        }
    }

    suspend fun getWeeklyNutrition(): Result<List<Pair<Int, Int>>> {
        val weeklyNutritionList = mutableListOf<Float>().apply { repeat(7) { add(0f) } }
        val weekList = mutableListOf<Triple<Int, Int, Int>>() // 0인덱스 = 오늘
        return kotlin.runCatching {
            val calendar = Calendar.getInstance()

            for (i in 0 until 7) {
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH) + 1
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                weekList.add(Triple(year, month, day))
                calendar.add(Calendar.DAY_OF_YEAR, -1)
            }

            foodInfoDao.getAll().first().forEach { foodInfo ->
                val (y, m, d) = foodInfo.date.toDate("YYYY-MM-dd").split('-').map { it.toInt() }
                for (i in 1..6) {
                    if (weekList[i].third == d && weekList[i].second == m && weekList[i].first == y) {
                        weeklyNutritionList[i] += foodInfo.calories
                    }
                }
            }
            weeklyNutritionList.mapIndexed { index, it -> Pair(weekList[index].third, it.toInt()) }
        }
    }
}
