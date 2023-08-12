package com.konkuk.history.data.datasource.statistic

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class StatCSVParser @Inject constructor(@ApplicationContext private val context: Context) {
    suspend fun getItemList(resId: Int): Result<List<NutritionStat>> {
        return kotlin.runCatching {
            withContext(Dispatchers.IO) {
                val inputStream = context.resources.openRawResource(resId)
                val br = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))

                var line: String? = ""
                val table = mutableListOf<List<String>>()
                val list = mutableListOf<NutritionStat>()

                while (br.readLine().also { line = it } != null) {
                    table.add(line!!.split(','))
                }

                for (j in 1..table[0].lastIndex) {
                    list.add(
                        NutritionStat(
                            table[1][j].toFloat().toInt(),
                            table[2][j].toFloat().toInt(),
                            table[3][j].toFloat().toInt(),
                            table[4][j].toFloat().toInt(),
                            table[5][j].toFloat().toInt(),
                            table[6][j].toFloat().toInt(),
                        ),
                    )
                }
                list
            }
        }
    }
}

data class NutritionStat(
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carbohydrates: Int,
    val sugar: Int,
    val sodium: Int,
) {
    companion object {
        val ageList = listOf(0..2, 3..5, 6..11, 12..18, 19..29, 50..64, 51..100)
        fun getAgeIndex(age: Int) =
            when (age) {
                in 0..2 -> 0
                in 3..5 -> 1
                in 6..11 -> 2
                in 12..18 -> 3
                in 19..29 -> 4
                in 50..64 -> 5
                else -> 6
            }
    }
}
