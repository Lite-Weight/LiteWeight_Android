package com.konkuk.capture.data.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.konkuk.common.data.FoodInfo

class SearchFoodPagingSource(
    private val name: String,
    private val searchFoodDataSource: SearchFoodDataSource,
) : PagingSource<Int, FoodInfo>() {
    override suspend fun load(
        params: LoadParams<Int>,
    ): LoadResult<Int, FoodInfo> {
        kotlin.runCatching {
            val pageNo = params.key ?: 1
            searchFoodDataSource.searchFood(name, pageNo).onSuccess { items ->
                if (items.isEmpty()) return LoadResult.Error(IllegalStateException("아이탬 끝"))
                return LoadResult.Page(
                    data = items,
                    prevKey = null, // Only paging forward.
                    nextKey = pageNo + 1,
                )
            }
        }
        return LoadResult.Error(IllegalStateException("검색 결과 오류 발생"))
    }

    override fun getRefreshKey(state: PagingState<Int, FoodInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
