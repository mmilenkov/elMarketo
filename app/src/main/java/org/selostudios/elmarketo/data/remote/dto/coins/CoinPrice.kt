package org.selostudios.elmarketo.data.remote.dto.coins

import kotlinx.serialization.Serializable

@Serializable
data class CoinPrice(
    private val fields: Map<String, String?>
) {
    fun getPrice(currency: String):Double =
        checkNotNull(getPriceOrNull(currency))

    fun getPriceOrNull(currency: String): Double? =
        fields[currency.lowercase()]?.toDoubleOrNull()

    fun getMarketCapOrNull(currency: String): Double? =
        fields["${currency.lowercase()}_market_cap"]?.toDoubleOrNull()

    fun getMarketCap(currency: String): Double =
        checkNotNull(getMarketCapOrNull(currency))

    fun get24HourVolOrNull(currency: String): Double? =
        fields["${currency.lowercase()}_24h_vol"]?.toDoubleOrNull()

    fun get24HourVol(currency: String): Double =
        checkNotNull(get24HourVolOrNull(currency))

    fun get24HourChangeOrNull(currency: String): Double? =
        fields["${currency.lowercase()}_24h_change"]?.toDoubleOrNull()

    fun get24HourChange(currency: String): Double =
        checkNotNull(get24HourChangeOrNull(currency))

    fun getRawField(key: String): String? = fields[key]

    override fun toString(): String = fields.toString()
}