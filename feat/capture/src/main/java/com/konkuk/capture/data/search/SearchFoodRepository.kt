package com.konkuk.capture.data.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.konkuk.common.data.FoodInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchFoodRepository @Inject constructor(private val searchFoodDatasource: SearchFoodDataSource) {
    fun getSearchedItems(name: String): Flow<PagingData<FoodInfo>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { loadSearchedFoodItemsPage(name) },
        )
            .flow
    }

    private fun loadSearchedFoodItemsPage(name: String): SearchFoodPagingSource {
        return SearchFoodPagingSource(name, searchFoodDatasource)
    }
}
