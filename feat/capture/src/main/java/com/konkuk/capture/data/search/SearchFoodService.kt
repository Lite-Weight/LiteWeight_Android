package com.konkuk.capture.data.search

import retrofit2.Response
import retrofit2.http.GET

interface SearchFoodService {
    @GET
    suspend fun searchFoodService(name: String, pageNo: Int): Response<FoodsResponse>
}
