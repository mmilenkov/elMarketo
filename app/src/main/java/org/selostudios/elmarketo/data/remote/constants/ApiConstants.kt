package org.selostudios.elmarketo.data.remote.constants

object ApiConstants {
    //Base path
    const val API_HOST = "api.coingecko.com"
    const val BASE_PATH = "/api/v3"

    //Endpoints
    const val GET_PRICE = "simple/price"
    const val GET_VS_CURRENCIES = "simple/supported_vs_currencies"
    const val GET_COIN_LIST = "coins/list"
    const val GET_COIN_MARKETS = "coins/markets"

    //Orders
    const val MARKET_CAP_DESC = "market_cap_desc"
    const val MARKET_CAP_ASC = "market_cap_asc"
    const val GECKO_DESC = "gecko_desc"
    const val GECKO_ASC = "gecko_asc"
    const val VOLUME_ASC = "volume_asc"
    const val VOLUME_DESC = "volume_desc"
    const val ID_ASC = "id_asc"
    const val ID_DESC = "id_desc"

    //Params
    const val IDS = "ids"
    const val VS_CURRENCIES = "vs_currencies"
    const val INCLUDE_MARKET_CAP = "include_market_cap"
    const val INCLUDE_24H_VOLUME = "include_24hr_vol"
    const val INCLUDE_24H_CHANGE = "include_24hr_change"
    const val INCLUDE_LAST_UPDATED_AT = "include_last_updated_at"
    const val INCLUDE_PLATFORM = "include_platform"
    const val VS_CURRENCY = "vs_currency"
    const val ORDER = "order"
    const val PAGE = "page"
    const val PER_PAGE = "per_page"
    const val SPARKLING = "sparkling"
    const val PRICE_CHANGE_PERCENTAGE = "price_change_percentage"

}