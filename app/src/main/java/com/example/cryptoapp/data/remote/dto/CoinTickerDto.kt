package com.example.cryptoapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinTickerDto(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    @SerialName("circulating_supply")
    val circulatingSupply: Long? = null,
    @SerialName("max_supply")
    val maxSupply: Long? = null,
    val quotes: Map<String, QuoteDto>
)

@Serializable
data class QuoteDto(
    val price: Double,
    @SerialName("market_cap")
    val marketCap: Double,
    @SerialName("volume_24h")
    val volume24h: Double,
    @SerialName("percent_change_24h")
    val percentChange24h: Double,
    @SerialName("ath_price")
    val athPrice: Double? = null
)
