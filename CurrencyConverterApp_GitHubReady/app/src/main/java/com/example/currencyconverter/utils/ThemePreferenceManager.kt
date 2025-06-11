 package com.example.currencyconverter.utils

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("theme_prefs")

class ThemePreferenceManager(private val context: Context) {
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")

    suspend fun saveTheme(darkMode: Boolean) {
        context.dataStore.edit { it[DARK_MODE_KEY] = darkMode }
    }

    suspend fun isDarkMode(): Boolean {
        return context.dataStore.data.map { it[DARK_MODE_KEY] ?: false }.first()
    }
}
