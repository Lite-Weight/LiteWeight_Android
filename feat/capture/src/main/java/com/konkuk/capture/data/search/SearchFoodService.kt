package com.konkuk.capture.data.search

import com.konkuk.capture.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchFoodService {

    @GET(API.SEARCH_FOOD_INFO)
    suspend fun searchFoodService(
        @Query("desc_kor") name: String,
        @Query("pageNo") pageNo: Int,
        @Query("type") type: String = API.TYPE,
        @Query("serviceKey", encoded = true) serviceKey: String = BuildConfig.DATA_API_KEY,
    ): Response<FoodsResponse>
}
