package com.konkuk.common.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodInfoDao {
    @Query("SELECT * FROM foodInfo")
    fun getAll(): Flow<List<FoodInfo>>

    @Query("SELECT * FROM foodInfo WHERE id = :id")
    suspend fun getFoodInfo(id: Long): FoodInfo

    @Insert
    suspend fun insert(foodInfo: FoodInfo)

    @Delete
    suspend fun delete(foodInfo: FoodInfo)
}
