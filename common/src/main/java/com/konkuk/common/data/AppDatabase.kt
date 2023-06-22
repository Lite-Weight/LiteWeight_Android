package com.konkuk.common.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FoodInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodInfoDao(): FoodInfoDao
}
