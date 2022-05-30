package org.selostudios.elmarketo.data.remote.dto.coins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinMarketList(
    @SerialName("items")
    val markets: List<CoinMarket>,
    val total: Int,
    val perPage: Int,
    val nextPage: Int?,
    val previousPage: Int?
)
