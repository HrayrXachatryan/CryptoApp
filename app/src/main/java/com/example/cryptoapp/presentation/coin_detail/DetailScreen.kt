package com.example.cryptoapp.presentation.coin_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.cryptoapp.domain.model.Coin
import java.util.Locale

@Composable
fun DetailScreen(
    coin: Coin,
    viewModel: DetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                IconButton(onClick = { /* TODO: Favorite logic */ }) {
                    Icon(
                        imageVector = Icons.Default.StarBorder,
                        contentDescription = "Favorite",
                        tint = Color.White
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF101010))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier.weight(1f).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066FF)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Buy", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                OutlinedButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier.weight(1f).height(56.dp),
                    border = BorderStroke(1.dp, Color(0xFF0066FF)),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF0066FF))
                ) {
                    Text("Sell", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        },
        containerColor = Color(0xFF101010)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // 1. Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = "https://static.coinpaprika.com/coin/${coin.id}/logo.png",
                    contentDescription = null,
                    modifier = Modifier.size(56.dp).clip(CircleShape).background(Color.DarkGray.copy(alpha = 0.3f)),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = coin.name, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Text(text = coin.symbol, color = Color.Gray, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Price
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = String.format(Locale.US, "$%,.2f", coin.priceUsd),
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(12.dp))
                val isPositive = coin.changePercent24Hr >= 0
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Icon(
                        imageVector = if (isPositive) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                        contentDescription = null,
                        tint = if (isPositive) Color(0xFF00FF88) else Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = String.format(Locale.US, "%s%.2f%%", if (isPositive) "+" else "", coin.changePercent24Hr),
                        color = if (isPositive) Color(0xFF00FF88) else Color.Red,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Range Tabs
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                listOf( "1D", "1W", "1M", "1Y",).forEach { range ->
                    val isSelected = state.selectedRange == range
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) Color(0xFF0066FF) else Color(0xFF1E1E1E))
                            .clickable { viewModel.onRangeSelected(range) }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = range, color = if (isSelected) Color.White else Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. THE GRAPH
            Box(modifier = Modifier.fillMaxWidth().height(250.dp)) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color(0xFF0066FF))
                } else if (state.error != null) {
                    Text(text = state.error!!, color = Color.Red, modifier = Modifier.align(Alignment.Center))
                } else {
                    CryptoLineChart(viewModel = viewModel)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 5. Stats
            Text(text = "Market Stats", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            val stats = listOf(
                Pair("Market Cap", formatLargeNumber(coin.marketCapUsd)),
                Pair("24h Volume", formatLargeNumber(coin.volume24h)),
                Pair("Circulating Supply", formatSupply(coin.circulatingSupply, coin.symbol)),
                Pair("Max Supply", if (coin.maxSupply > 0L) formatSupply(coin.maxSupply, coin.symbol) else "Unlimited"),
                Pair("Rank", "#${coin.rank}"),
                Pair("Type", coin.type.uppercase())
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                stats.chunked(2).forEach { rowStats ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        rowStats.forEach { stat ->
                            Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(Color(0xFF1A1A1A)).padding(16.dp)) {
                                Column {
                                    Text(text = stat.first, color = Color.Gray, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = stat.second, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// Форматирование чисел
private fun formatLargeNumber(value: Double): String {
    val trillion = 1_000_000_000_000.0
    val billion = 1_000_000_000.0
    val million = 1_000_000.0
    return when {
        value >= trillion -> String.format(Locale.US, "$%.2fT", value / trillion)
        value >= billion -> String.format(Locale.US, "$%.2fB", value / billion)
        value >= million -> String.format(Locale.US, "$%.2fM", value / million)
        else -> String.format(Locale.US, "$%,.2f", value)
    }
}

private fun formatSupply(value: Long, symbol: String): String {
    if (value == 0L) return "N/A"
    val billion = 1_000_000_000.0
    val million = 1_000_000.0
    val formattedValue = when {
        value.toDouble() >= billion -> String.format(Locale.US, "%.1fB", value.toDouble() / billion)
        value.toDouble() >= million -> String.format(Locale.US, "%.1fM", value.toDouble() / million)
        else -> String.format(Locale.US, "%,d", value)
    }
    return "$formattedValue $symbol"
}
