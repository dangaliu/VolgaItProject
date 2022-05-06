package com.example.volgaproject.retrofit

object Common {
    private var BASE_URL = "https://finnhub.io/api/v1/"
    val retrofitService: RetrofitService
        get() = RetrofitClient.getClient(baseUrl = BASE_URL).create(RetrofitService::class.java)
}