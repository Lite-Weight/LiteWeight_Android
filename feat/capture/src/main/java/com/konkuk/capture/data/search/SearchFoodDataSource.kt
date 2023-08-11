package com.konkuk.capture.data.search

import com.konkuk.common.data.FoodInfo
import javax.inject.Inject

class SearchFoodDataSource @Inject constructor(private val searchFoodService: SearchFoodService) {
    suspend fun searchFood(name: String, pageNo: Int): Result<List<FoodInfo>> {
        return kotlin.runCatching {
            requireNotNull(
                searchFoodService.searchFoodService(name, pageNo).body(),
            ).items.map { it.toFoodInfo() }
        }
    }
}
