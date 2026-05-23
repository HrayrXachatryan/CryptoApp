package com.example.cryptoapp.domain.use_case

import com.example.cryptoapp.data.remote.CoinCapApi
import com.example.cryptoapp.domain.model.Coin
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val api: CoinCapApi
) {
    suspend operator fun invoke(): List<Coin> {
        val tickers = api.getTickers()
        
        return tickers.map { ticker ->
            val usdQuote = ticker.quotes["USD"]
            Coin(
                id = ticker.id,
                name = ticker.name,
                symbol = ticker.symbol,
                rank = ticker.rank,
                isActive = true,
                priceUsd = usdQuote?.price ?: 0.0,
                changePercent24Hr = usdQuote?.percentChange24h ?: 0.0,
                marketCapUsd = usdQuote?.marketCap ?: 0.0,
                volume24h = usdQuote?.volume24h ?: 0.0,
                circulatingSupply = ticker.circulatingSupply ?: 0L,
                maxSupply = ticker.maxSupply ?: 0L,
                athPrice = usdQuote?.athPrice ?: 0.0,
                type = if (ticker.id.contains("token", ignoreCase = true)) "token" else "coin"
            )
        }
    }
}
