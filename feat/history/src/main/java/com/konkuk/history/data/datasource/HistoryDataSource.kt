package com.konkuk.history.data.datasource

import android.util.Log
import com.konkuk.common.Extension.toDate
import com.konkuk.common.data.FoodInfo
import com.konkuk.common.data.FoodInfoDao
import com.konkuk.history.domain.model.HistoryItemModel
import com.konkuk.history.domain.model.toHistoryItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryDataSource @Inject constructor(private val foodInfoDao: FoodInfoDao) {
    fun getFoodHistory(year: Int, month: Int, day: Int): Result<Flow<List<HistoryItemModel>>> {
        return kotlin.runCatching {
            Log.d("tag HistoryDataSource", "target $year $month $day")
            foodInfoDao.getAll().map { foodInfos: List<FoodInfo> ->
                foodInfos.filter {
                    val (y, m, d) = it.date.toDate("YYYY-MM-DD").split('-').map { it.toInt() }
                    year == y && month == m && day == d
                }.map { foodInfo ->
                    foodInfo.toHistoryItemModel()
                }
            }
        }
    }
}
