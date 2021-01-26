package com.example.searchkey.core.api

object ApiService {
    fun mAPIServices(baseUrl: String): IApiSerive {
        return RetrofitClient(baseUrl).client.create(IApiSerive::class.java)
    }
}