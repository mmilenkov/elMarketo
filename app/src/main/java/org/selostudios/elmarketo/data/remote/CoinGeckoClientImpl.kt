package org.selostudios.elmarketo.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.selostudios.elmarketo.data.remote.constants.ApiConstants
import org.selostudios.elmarketo.data.remote.dto.coins.CoinList
import org.selostudios.elmarketo.data.remote.dto.coins.CoinMarketList
import org.selostudios.elmarketo.data.remote.dto.coins.CoinPrice
import org.selostudios.elmarketo.data.remote.error.ApiError
import org.selostudios.elmarketo.data.remote.error.ApiException

internal val json = Json {
    isLenient = true
    ignoreUnknownKeys = true
    coerceInputValues = true
    useAlternativeNames = false
    prettyPrint =  true
}

typealias CoinPriceResultMap = Map<String, Map<String, String?>>

class CoinGeckoClientImpl: CoinGeckoClient {

    private val _instance = HttpClient(Android) {
        defaultRequest {
            url.protocol = URLProtocol.HTTPS
            url.host = ApiConstants.API_HOST
            url.path(ApiConstants.BASE_PATH, url.encodedPath)
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(json)
        }
    }.apply {
        sendPipeline.intercept(HttpSendPipeline.Before) {
            try {
                proceed()
            } catch (e: Throwable) {
                throw ApiException(e)
            }
        }
    }

    private suspend inline fun <reified T> HttpResponse.bodyOrThrow(): T {
        return if (status == HttpStatusCode.OK) {
            body()
        } else {
            val bodyText = bodyAsText()
            val bodyError = try {
                json.decodeFromString<ErrorBody>(bodyText).error
            } catch (e: SerializationException) {
                null
            }
            throw ApiException(ApiError(status.value, bodyError))
        }
    }

    override suspend fun getPrice(
        ids: String, //csv
        vsCurrencies: String, //csv
        includeMarketCap: String, //boolean def = false
        include24HourVolume: String, //boolean def = false
        include24HourChange: String, //boolean def = false
        includeLastUpdatedAt: String //boolean def = false
    ): Map<String, CoinPrice> =
        _instance.get(ApiConstants.GET_PRICE) {
            parameter(ApiConstants.IDS, ids)
            parameter(ApiConstants.VS_CURRENCIES, vsCurrencies)
            parameter(ApiConstants.INCLUDE_MARKET_CAP, includeMarketCap)
            parameter(ApiConstants.INCLUDE_24H_VOLUME, include24HourVolume)
            parameter(ApiConstants.INCLUDE_24H_CHANGE, include24HourChange)
            parameter(ApiConstants.INCLUDE_LAST_UPDATED_AT, includeLastUpdatedAt)
        }.body<CoinPriceResultMap>() //Typealias
            .mapValues { (_,v) -> CoinPrice(v) }

    override suspend fun getSupportedVSCurrencies(): List<String> =
        _instance.get(ApiConstants.GET_VS_CURRENCIES).bodyOrThrow()

    override suspend fun getCoinList(includePlatform: Boolean): List<CoinList> =
        _instance.get(ApiConstants.GET_COIN_LIST) {
            parameter(ApiConstants.INCLUDE_PLATFORM, includePlatform)
        }.bodyOrThrow()

    override suspend fun getCoinMarkets(
        vsCurrency: String, //Singular currency
        ids: String?, //csv
        order: String?, //csv for ordering results default = MARKET_CAP_DESC
        perPage: Int?, //1..250
        page: Int?,
        sparkling: Boolean,
        priceChangePercentage: String? //csv (1h,24h,7d,14d,30d,200d,1y)
    ): CoinMarketList =
        _instance.get(ApiConstants.GET_COIN_MARKETS) {
            parameter(ApiConstants.VS_CURRENCY, vsCurrency)
            parameter(ApiConstants.IDS, ids)
            parameter(ApiConstants.ORDER, order)
            parameter(ApiConstants.PER_PAGE, perPage)
            parameter(ApiConstants.PAGE, page)
            parameter(ApiConstants.SPARKLING, sparkling)
            parameter(ApiConstants.PRICE_CHANGE_PERCENTAGE, priceChangePercentage)
        }.bodyOrThrow()

    @Serializable
    private data class ErrorBody(val error: String?)
}