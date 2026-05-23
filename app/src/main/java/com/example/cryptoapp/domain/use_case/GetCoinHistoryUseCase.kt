package com.example.cryptoapp.domain.use_case

import com.example.cryptoapp.data.remote.CoinCapApi
import com.example.cryptoapp.domain.model.CoinPricePoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetCoinHistoryUseCase @Inject constructor(
    private val api: CoinCapApi
) {
    /**
     * Получает исторические данные цен криптовалюты из Coinpaprika.
     * * @param coinId - ID монеты (например, "btc-bitcoin")
     * @param range - временной период (1D, 1W, 1M, 1Y)
     * @return Список точек для графика (время и цена)
     */
    suspend operator fun invoke(coinId: String, range: String): List<CoinPricePoint> {
        val calendar = Calendar.getInstance()


        val interval = when (range) {
            "1D" -> {
                calendar.add(Calendar.DAY_OF_YEAR, -1)
                "1h"
            }
            "1W" -> {
                calendar.add(Calendar.DAY_OF_YEAR, -7)
                "24h"
            }
            "1M" -> {
                calendar.add(Calendar.MONTH, -1)
                "24h"
            }
            "1Y", "ALL" -> {
                calendar.add(Calendar.YEAR, -1)
                "24h"
            }
            else -> {
                calendar.add(Calendar.DAY_OF_YEAR, -1)
                "1h"
            }
        }

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val startStr = sdf.format(calendar.time)

        return try {
            val response = api.getCoinHistory(coinId, startStr, interval)

            response.map { dto ->
                val date = sdf.parse(dto.timestamp)
                CoinPricePoint(
                    timestamp = date?.time ?: 0L,
                    price = dto.price
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}