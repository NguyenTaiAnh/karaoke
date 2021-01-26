package com.example.searchkey.core.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(val baseUrl: String) {
    val client: Retrofit
        get() {
            return Retrofit.Builder().apply {
                baseUrl(baseUrl)
                addConverterFactory(GsonConverterFactory.create())
            }.build()
        }
}