package com.konkuk.capture.data.search

import com.konkuk.common.data.FoodInfo
import javax.inject.Inject

class SearchFoodDataSource @Inject constructor(private val searchFoodService: SearchFoodService) {
    suspend fun searchFood(name: String, pageNo: Int): Result<List<FoodInfo>> {
        val searchName = name.trim()
        if (searchName.isBlank()) return Result.success(emptyList())
        kotlin.runCatching {
            val response = searchFoodService.searchFoodService(searchName, pageNo)
            if (response.isSuccessful) {
                return Result.success(requireNotNull(response.body()).body.items.map { it.toFoodInfo() })
            }
        }
        return Result.success(emptyList())
    }
}
