package org.selostudios.elmarketo.data.remote.dto.coins

import kotlinx.serialization.Serializable

@Serializable
data class CoinList(
    val id: String,
    val symbol: String,
    val name: String,
    val platforms: Map<String, String> = emptyMap()
)
