package com.example.cryptoapp.data.remote

import com.example.cryptoapp.data.remote.dto.CoinDto
import com.example.cryptoapp.data.remote.dto.CoinHistoryDto
import com.example.cryptoapp.data.remote.dto.CoinTickerDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinCapApi {
    @GET("coins")
    suspend fun getCoins(): List<CoinDto>

    @GET("tickers")
    suspend fun getTickers(): List<CoinTickerDto>

    @GET("tickers/{coinId}/historical")
    suspend fun getCoinHistory(
        @Path("coinId") coinId: String,
        @Query("start") start: String,
        @Query("interval") interval: String
    ): List<CoinHistoryDto>
}
