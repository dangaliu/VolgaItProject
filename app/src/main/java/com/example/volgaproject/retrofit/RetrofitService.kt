package com.example.volgaproject.retrofit

import com.example.volgaproject.model.Quote
import com.example.volgaproject.model.StockSymbol
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("stock/symbol")
    fun getStockSymbols(
        @Query("exchange") exchange: String,
        @Query("token") token: String
    ): Call<List<StockSymbol>>

    @GET("quote")
    fun getQuote(
        @Query("symbol") symbol: String,
        @Query("token") token: String
    ): Call<Quote>
}