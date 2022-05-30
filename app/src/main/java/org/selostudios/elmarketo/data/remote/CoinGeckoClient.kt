package org.selostudios.elmarketo.data.remote

import org.selostudios.elmarketo.data.remote.dto.coins.CoinList
import org.selostudios.elmarketo.data.remote.dto.coins.CoinMarketList
import org.selostudios.elmarketo.data.remote.dto.coins.CoinPrice

interface CoinGeckoClient {
    suspend fun getCoinList(includePlatform: Boolean): List<CoinList>

    suspend fun getPrice(
        ids: String,
        vsCurrencies: String,
        includeMarketCap: String,
        include24HourVolume: String,
        include24HourChange: String,
        includeLastUpdatedAt: String
    ): Map<String, CoinPrice>

    suspend fun getSupportedVSCurrencies(): List<String>

    suspend fun getCoinMarkets(
        vsCurrency: String,
        ids: String?,
        order: String?,
        perPage: Int?,
        page: Int?,
        sparkling: Boolean,
        priceChangePercentage: String?
    ): CoinMarketList
}