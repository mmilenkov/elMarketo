package org.selostudios.elmarketo.data.remote

import org.selostudios.elmarketo.data.remote.dto.CoinList

interface CoinGeckoClient {
    suspend fun getCoinList(includePlatform: Boolean): List<CoinList>
}