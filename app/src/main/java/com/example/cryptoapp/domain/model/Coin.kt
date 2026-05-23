package com.example.cryptoapp.domain.model

data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val isActive: Boolean,
    val priceUsd: Double = 0.0,
    val changePercent24Hr: Double = 0.0,
    val marketCapUsd: Double = 0.0,
    val volume24h: Double = 0.0,
    val circulatingSupply: Long = 0,
    val maxSupply: Long = 0,
    val athPrice: Double = 0.0,
    val type: String = "coin"
)
