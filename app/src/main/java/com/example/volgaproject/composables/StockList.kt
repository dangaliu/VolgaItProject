package com.example.volgaproject.composables

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.volgaproject.model.StockAndPrice
import com.example.volgaproject.stockAndPrices
import com.example.volgaproject.subscribeSymbol
import com.example.volgaproject.unsubscribeSymbol

private val subscribedSymbols = ArrayList<Int>()

@Composable
fun StockList(list: SnapshotStateList<StockAndPrice>) {
    val items = remember { list }
    val listState = rememberLazyListState()
    val allIndexes = ArrayList<Int>()
    for (i in 0 until items.size) allIndexes.add(i)
    LazyColumn(verticalArrangement = Arrangement.SpaceAround, state = listState) {
        items(items = items)
        { item ->
            Stock(
                StockAndPrice(
                    stock = item.stock,
                    price = item.price ?: 0.0
                )
            )
            Spacer(Modifier.height(4.dp))
        }
    }
    val visibleIndexesList = listState.layoutInfo.visibleItemsInfo
    val visibleIndexes = ArrayList<Int>()
    for (i in visibleIndexesList) {
        visibleIndexes.add(i.index)
    }
    val counter = remember { mutableStateOf(0) }
    if (stockAndPrices.size > 0) {
        visibleIndexesList.forEach {
            if (!(items[it.index].isSend)) {
                subscribeSymbol(items[it.index].stock)
                subscribedSymbols.add(it.index)
                println("sub")
                counter.value++
                items[it.index].isSend = true
            }
        }
    }
    val iterator = subscribedSymbols.iterator()
    iterator.forEach {
        if (!(visibleIndexes.contains(it))) {
            if (items[it].isSend) {
                unsubscribeSymbol(items[it].stock)
                iterator.remove()
                println("unsub")
                counter.value--
                items[it].isSend = false
            }
        }
    }
}
