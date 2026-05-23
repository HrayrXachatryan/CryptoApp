package com.example.cryptoapp.domain.model

data class Transaction(
    val id: String,
    val type: TransactionType,
    val coinName: String,
    val coinSymbol: String,
    val amount: Double,
    val price: Double,
    val date: String,
    val status: TransactionStatus
)

enum class TransactionType { BUY, SELL }
enum class TransactionStatus { COMPLETED, PENDING, FAILED }
