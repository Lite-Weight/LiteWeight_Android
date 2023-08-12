package com.konkuk.capture.domain

import androidx.paging.PagingData
import com.konkuk.capture.data.search.SearchFoodRepository
import com.konkuk.common.data.FoodInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchFoodNameUseCase @Inject constructor(private val searchFoodRepository: SearchFoodRepository) {
    operator fun invoke(name: String): Flow<PagingData<FoodInfo>> {
        return searchFoodRepository.getSearchedItems(name)
    }
}
