 package com.example.currencyconverter.data.api

import okhttp3.OkHttpClient
import okhttp3.Request
import com.example.currencyconverter.data.model.CurrencyResponse
import com.google.gson.Gson

class CurrencyApiService {

    private val client = OkHttpClient()
    private val gson = Gson()

    fun getRates(baseCurrency: String): CurrencyResponse? {
        val url = "https://api.exchangerate-api.com/v4/latest/$baseCurrency"

        val request = Request.Builder().url(url).build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return null
            val body = response.body?.string() ?: return null
            return gson.fromJson(body, CurrencyResponse::class.java)
        }
    }
}
