package com.example.searchkey.core.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiSerive {
    @GET("complete/search?output=firefox&hl=vi")
    fun getKeyword(@Query("q") q: String): Call<Any>
}