package org.selostudios.elmarketo.data.remote.dto.coins

import kotlinx.serialization.Serializable

@Serializable
data class Roi(
    val times: Float = 0F,
    val currency: String? = null,
    val percentage: Float = 0F
)
