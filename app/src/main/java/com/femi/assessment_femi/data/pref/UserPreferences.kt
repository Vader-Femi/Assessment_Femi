package com.femi.assessment_femi.data.pref

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "USER_PREFERENCES")

class UserPreferences(private val context: Context) {

    private object PreferencesKeys {
        val KEY_SELECTED_BRAND = stringPreferencesKey("KEY_SELECTED_BRAND")
    }

    val selectedBrand: Flow<String>
        get() = context.dataStore.data.map { preference ->
            preference[PreferencesKeys.KEY_SELECTED_BRAND] ?: ""
        }

    suspend fun selectedBrand(brand: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.KEY_SELECTED_BRAND] = brand
        }
    }

}