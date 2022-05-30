package org.selostudios.elmarketo.data.remote.dto.coins

import kotlinx.serialization.Serializable

@Serializable
data class SparklineIn7d(
    val price: List<Double>? = null
)
