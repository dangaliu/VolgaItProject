package com.example.volgaproject.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.volgaproject.model.StockAndPrice

@Composable
fun Stock(
    stockAndPrice: StockAndPrice
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color(0xff333333),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(48.dp)
            .fillMaxWidth(),
        elevation = 5.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(16.dp))
            Text(
                text = stockAndPrice.stock,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stockAndPrice.price.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Spacer(Modifier.width(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StockPreview() {
    Stock(StockAndPrice(stock = "AAPL", price = 160.34))
}