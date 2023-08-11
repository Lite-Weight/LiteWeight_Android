package com.konkuk.capture.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.konkuk.capture.data.search.API
import com.konkuk.capture.data.search.SearchFoodService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newRequest = chain.request().newBuilder() // add header
                .build()
            chain.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }

        return Retrofit.Builder()
            .baseUrl(API.BASE_URL)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory(API.CONTENT_TYPE.toMediaType()),
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideSearchFoodService(retrofit: Retrofit): SearchFoodService {
        return retrofit.create(SearchFoodService::class.java)
    }
}
