package net.rossmanges.wmtakehome.repository

import android.util.Log
import com.google.gson.GsonBuilder
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import net.rossmanges.wmtakehome.data.CountryList

/**
 * The repository for fetching Country data.
 */
class CountryRepository() {
    private val url = "https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json"

    /**
     * Fetches predefined country data in json format from an http endpoint and uses
     * GSON to inflate the [CountryList] object.
     *
     * TODO - consider returning a Sealed class
     * TODO - consider convert from GSON to Kotlin Serialization.
     */
    suspend fun fetchCountryData(): CountryList? {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                gson()
            }
        }
        Log.v("json", "fetchCountryData() invoked")
        return try {
            val response:String = client.get(url).body()
            val gson = GsonBuilder().create()
            gson.fromJson(response, CountryList::class.java)
        } catch (e: Exception) {
            Log.e("json", "fetchCountryData() Error fetching data: ${e.message}")
            null
        } finally {
            client.close()
        }
    }
}