package com.example.volgaproject.model

data class StockAndPrice(
    val stock: String,
    val price: Double?,
    var isSend: Boolean = false
)
