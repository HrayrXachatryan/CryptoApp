package com.example.cryptoapp.presentation.history

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoapp.domain.model.Transaction
import com.example.cryptoapp.domain.model.TransactionStatus
import com.example.cryptoapp.domain.model.TransactionType
import com.example.cryptoapp.presentation.history.components.TransactionHistoryItem

@Composable
fun HistoryScreen(
    onHomeClick: () -> Unit
) {
    val transactions = remember {
        listOf(
            Transaction("1", TransactionType.BUY, "Bitcoin", "BTC", 0.0234, 1602.50, "Apr 12, 2026 • 09:24 AM", TransactionStatus.COMPLETED),
            Transaction("2", TransactionType.SELL, "Ethereum", "ETH", 0.8500, 2758.99, "Apr 11, 2026 • 02:15 PM", TransactionStatus.COMPLETED),
            Transaction("3", TransactionType.BUY, "Solana", "SOL", 15.2000, 2166.91, "Apr 10, 2026 • 11:42 AM", TransactionStatus.PENDING),
            Transaction("4", TransactionType.BUY, "Cardano", "ADA", 1250.0, 729.25, "Apr 09, 2026 • 04:33 PM", TransactionStatus.COMPLETED),
            Transaction("5", TransactionType.SELL, "Ripple", "XRP", 500.0, 306.35, "Apr 08, 2026 • 10:18 AM", TransactionStatus.FAILED)
        )
    }



    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF1A1A1A),
                tonalElevation = 0.dp,
                modifier = Modifier.border(0.5.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = onHomeClick,
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onHomeClick,
                    icon = { Icon(Icons.Default.ShowChart, contentDescription = "Markets") },
                    label = { Text("Markets") },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.History, contentDescription = "History") },
                    label = { Text("History") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF0066FF),
                        selectedTextColor = Color(0xFF0066FF),
                        indicatorColor = Color.Transparent
                    )
                )
            }
        },
        containerColor = Color(0xFF101010)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Transaction History",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Track all your trading activity",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
                

            }
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(transactions) { transaction ->
                    TransactionHistoryItem(transaction = transaction)
                }
            }
        }
    }
}
