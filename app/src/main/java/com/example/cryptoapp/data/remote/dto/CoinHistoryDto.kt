package com.example.cryptoapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryDto(
    val timestamp: String,
    val price: Double
)
