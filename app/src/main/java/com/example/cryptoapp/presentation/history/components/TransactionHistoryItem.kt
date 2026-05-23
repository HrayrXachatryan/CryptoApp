package com.example.cryptoapp.presentation.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallMade
import androidx.compose.material.icons.filled.CallReceived
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoapp.domain.model.Transaction
import com.example.cryptoapp.domain.model.TransactionStatus
import com.example.cryptoapp.domain.model.TransactionType
import java.util.Locale

@Composable
fun TransactionHistoryItem(
    transaction: Transaction,
    modifier: Modifier = Modifier
) {
    val isBuy = transaction.type == TransactionType.BUY
    val statusColor = when (transaction.status) {
        TransactionStatus.COMPLETED -> Color(0xFF00FF88)
        TransactionStatus.PENDING -> Color(0xFFFFB800)
        TransactionStatus.FAILED -> Color(0xFFFF4D4D)
    }
    val statusIcon = when (transaction.status) {
        TransactionStatus.COMPLETED -> Icons.Default.CheckCircle
        TransactionStatus.PENDING -> Icons.Default.Schedule
        TransactionStatus.FAILED -> Icons.Default.Error
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFF1A1A1A))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Left Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isBuy) Icons.Default.CallReceived else Icons.Default.CallMade,
                    contentDescription = null,
                    tint = if (isBuy) Color(0xFF00FF88) else Color(0xFFFF4D4D),
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Info
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${if (isBuy) "Buy" else "Sell"} ${transaction.coinName}",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = transaction.coinSymbol,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = transaction.date,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                // Status Badge
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(statusColor.copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = statusIcon,
                        contentDescription = null,
                        tint = statusColor,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = transaction.status.name.lowercase().replaceFirstChar { it.uppercase() },
                        color = statusColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Amount and Price
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${if (isBuy) "+" else "-"}${String.format(Locale.US, "%.4f", transaction.amount)}",
                    color = if (isBuy) Color(0xFF00FF88) else Color(0xFFFF4D4D),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = String.format(Locale.US, "$%,.2f", transaction.price),
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}
