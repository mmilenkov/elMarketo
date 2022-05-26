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
import org.selostudios.elmarketo.data.remote.dto.CoinList
import org.selostudios.elmarketo.data.remote.error.ApiError
import org.selostudios.elmarketo.data.remote.error.ApiException

internal val json = Json {
    isLenient = true
    ignoreUnknownKeys = true
    coerceInputValues = true
    useAlternativeNames = false
    prettyPrint =  true
}

class CoinGeckoClientImpl: CoinGeckoClient {

    private val API_HOST = "api.coingecko.com"
    private val BASE_PATH = "/api/v3"

    private val _instance = HttpClient(Android) {
        defaultRequest {
            url.protocol = URLProtocol.HTTPS
            url.host = API_HOST
            url.path(BASE_PATH, url.encodedPath)
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

    override suspend fun getCoinList(includePlatform: Boolean): List<CoinList> =
        _instance.get("coins/list") {
            parameter("include_platform", includePlatform)
        }.bodyOrThrow()

    @Serializable
    private data class ErrorBody(val error: String?)
}