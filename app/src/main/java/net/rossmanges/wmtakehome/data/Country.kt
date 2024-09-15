package net.rossmanges.wmtakehome.data

import com.google.gson.annotations.SerializedName

class CountryList : ArrayList<Country>()

/**
 * Data class for storing country information from JSON data.
 */
data class Country(
    @SerializedName("capital") val capital: String,
    @SerializedName("code") val code: String,
    @SerializedName("currency") val currency: Currency,
    @SerializedName("flag") val flag: String?,
    @SerializedName("language") val language: Language,
    @SerializedName("name") val name: String,
    @SerializedName("region") val region: String?
)

/**
 * Data class for storing currency information from JSON data.
 */
data class Currency(
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String?
)

/**
 * Data class for storing language information from JSON data.
 */
data class Language(
    @SerializedName("code") val code: String?,
    @SerializedName("name") val name: String?
)

/* EXAMPLE JSON DATA
 *   {
 *     "capital": "Charlotte Amalie",
 *     "code": "VI",
 *     "currency": {
 *       "code": "USD",
 *       "name": "United States dollar",
 *       "symbol": "$"
 *     },
 *     "flag": "https://restcountries.eu/data/vir.svg",
 *     "language": {
 *       "code": "en",
 *       "name": "English"
 *     },
 *     "name": "Virgin Islands (U.S.)",
 *     "region": "NA"
 *   }
 */
