 package com.example.currencyconverter.data.repository

import android.content.Context
import com.example.currencyconverter.data.api.CurrencyApiService
import com.example.currencyconverter.data.model.CurrencyResponse
import com.example.currencyconverter.utils.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurrencyRepository(private val context: Context) {

    private val apiService = CurrencyApiService()
    private val prefs = PreferenceManager(context)

    suspend fun convert(base: String, target: String): Pair<Double?, Boolean> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getRates(base)
            if (response != null) {
                prefs.saveLastRate(target, response.rates[target] ?: 0.0)
                Pair(response.rates[target], true)
            } else {
                Pair(prefs.getLastRate(target), false)
            }
        }
    }

    fun getSavedCurrency(key: String): String = prefs.getString(key, "USD")
    fun saveCurrency(key: String, value: String) = prefs.putString(key, value)
}
