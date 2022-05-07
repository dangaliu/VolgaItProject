package com.example.volgaproject

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.example.volgaproject.controller.symbols
import com.example.volgaproject.model.Data
import com.example.volgaproject.model.StockAndPrice
import com.example.volgaproject.model.Trades
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import java.util.concurrent.TimeUnit

var mWebSocket: WebSocket? = null
var stockAndPrices = mutableStateListOf<StockAndPrice>()
val apiKey = "c9r87vqad3i8g2roips0"
fun connectionWebSocket() {
    val httpClient = OkHttpClient.Builder()
        .pingInterval(40, TimeUnit.SECONDS)
        .build()

    val webSocketUrl = "wss://ws.finnhub.io?token=$apiKey"
    val request = Request.Builder()
        .url(webSocketUrl)
        .build()

    httpClient.newWebSocket(request, object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            mWebSocket = webSocket
            for (i in symbols) {
                stockAndPrices.add(StockAndPrice(i, 0.0))
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            Log.i("websocket", text)
            val data = Gson().fromJson(text, Trades::class.java)
            val iterator = stockAndPrices.listIterator()
            iterator.forEach { item ->
                for (symbol in data.data) {
                    if (item.stock == symbol.s) {
                        symbol.p?.let { price ->
                            iterator.set(item.copy(stock = item.stock, price = price))
                        }
                        Log.i(
                            "stockAndSymbols",
                            "${item.stock} - ${item.price}"
                        )
                    }
                }
            }

        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            println(code)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            println(code)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Log.i("websocket", t.stackTraceToString())
        }
    })
}

fun subscribeSymbol(symbol: String) {
    mWebSocket?.send("{\"type\":\"subscribe\",\"symbol\":\"$symbol\"}")
}
fun unsubscribeSymbol(symbol: String) {
    mWebSocket?.send("{\"type\":\"unsubscribe\",\"symbol\":\"$symbol\"}")
}
