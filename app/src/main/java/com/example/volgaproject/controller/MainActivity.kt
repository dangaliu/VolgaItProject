package com.example.volgaproject.controller

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.volgaproject.apiKey
import com.example.volgaproject.composables.StockList
import com.example.volgaproject.connectionWebSocket
import com.example.volgaproject.model.StockAndPrice
import com.example.volgaproject.model.StockSymbol
import com.example.volgaproject.retrofit.Common
import com.example.volgaproject.retrofit.Common.retrofitService
import com.example.volgaproject.retrofit.RetrofitService
import com.example.volgaproject.stockAndPrices
import com.example.volgaproject.ui.theme.VolgaProjectTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val symbols = ArrayList<String>()

class MainActivity : ComponentActivity() {
    lateinit var retrofitService: RetrofitService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retrofitService = Common.retrofitService
        getStockSymbolsList()
        setContent {
            App()
        }
    }
}

fun getStockSymbolsList() {
    retrofitService.getStockSymbols("US", apiKey).enqueue(object :
        Callback<List<StockSymbol>> {
        override fun onResponse(
            call: Call<List<StockSymbol>>,
            response: Response<List<StockSymbol>>
        ) {
            Log.i("response", response.code().toString())
            if (response.code() == 200) {
                for (stock in response.body() as List<StockSymbol>) {
                    symbols.add(stock.symbol)
                }
                connectionWebSocket()
            }
        }

        override fun onFailure(call: Call<List<StockSymbol>>, t: Throwable) {
            Log.i("response", t.message.toString())
            Log.i("response", t.message.toString())
        }
    })
}

@Composable
fun App() {
    Box(
        modifier = Modifier
            .background(color = Color.LightGray)
            .padding(vertical = 16.dp)
            .fillMaxSize()
    ) {
        StockList(list = stockAndPrices)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    App()
}