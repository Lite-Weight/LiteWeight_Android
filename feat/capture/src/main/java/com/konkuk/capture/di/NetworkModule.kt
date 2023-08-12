package com.konkuk.capture.di

import com.google.gson.Gson
import com.konkuk.capture.data.search.API
import com.konkuk.capture.data.search.SearchFoodService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
            val newRequest = chain.request().newBuilder().header("Content-Type", API.CONTENT_TYPE)
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
            prettyPrint = true
            isLenient = true
            coerceInputValues = true
        }

        return Retrofit.Builder()
            .baseUrl(API.BASE_URL)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create(Gson()),
                // json.asConverterFactory(API.CONTENT_TYPE.toMediaType()),
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideSearchFoodService(retrofit: Retrofit): SearchFoodService {
        return retrofit.create(SearchFoodService::class.java)
    }
}
