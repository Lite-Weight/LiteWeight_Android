package com.konkuk.common.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

    private val nameKey = stringPreferencesKey(NAME_KEY)
    private val ageKey = intPreferencesKey(AGE_KEY)
    private val genderKey = booleanPreferencesKey(GENDER_KEY)

    val nameFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[nameKey]
        }

    suspend fun setName(name: String) {
        context.dataStore.edit { settings ->
            settings[nameKey] = name
        }
    }

    val ageFlow: Flow<Int?> = context.dataStore.data
        .map { preferences ->
            preferences[ageKey]
        }

    suspend fun setAge(age: Int) {
        context.dataStore.edit { settings ->
            settings[ageKey] = age
        }
    }

    val genderFlow: Flow<Boolean?> = context.dataStore.data
        .map { preferences ->
            preferences[genderKey]
        }

    suspend fun setGender(gender: Boolean) {
        context.dataStore.edit { settings ->
            settings[genderKey] = gender
        }
    }

    companion object {
        const val DATASTORE_NAME = "settings"
        const val NAME_KEY = "nameKey"
        const val AGE_KEY = "ageKey"
        const val GENDER_KEY = "genderKey"
    }
}
