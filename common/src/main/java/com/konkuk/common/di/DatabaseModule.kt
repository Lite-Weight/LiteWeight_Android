package com.konkuk.common.di

import android.content.Context
import androidx.room.Room
import com.konkuk.common.data.AppDatabase
import com.konkuk.common.data.FoodInfoDao
import com.konkuk.common.data.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "FoodHistoryDatabase",
        ).build()
    }

    @Provides
    @Singleton
    fun provideFoodInfoDao(db: AppDatabase): FoodInfoDao {
        return db.foodInfoDao()
    }

    @Provides
    @Singleton
    fun provideUserRepository(@ApplicationContext context: Context): UserRepository {
        return UserRepository(context)
    }
}
