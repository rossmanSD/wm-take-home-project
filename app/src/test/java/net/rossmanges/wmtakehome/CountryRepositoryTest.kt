package net.rossmanges.wmtakehome

import io.ktor.client.HttpClient
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import net.rossmanges.wmtakehome.data.Country
import net.rossmanges.wmtakehome.data.Currency
import net.rossmanges.wmtakehome.data.Language
import net.rossmanges.wmtakehome.repository.CountryRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

/**
 * NOTE this test is not complete, the mocking of the http client is not working.
 */
class CountryRepositoryTest {
    @Test
    fun `fetchCountryData returns valid data`() = runBlocking {
        // Mock the HttpClient
        val mockHttpClient = mockk<HttpClient>()

        // Mock the network call to return a list of countries
        val mockCountries = listOf(
            Country(
                "Charlotte Amalie",
                "VI",
                Currency("USD", "United States dollar", "$"),
                "https://restcountries.eu/data/vir.svg",
                Language("en", "English"),
                "Virgin Islands (U.S.)",
                "NA"
            ),
            Country(
                "",
                "UM",
                Currency("USD", "United States Dollar", "$"),
                "https://restcountries.eu/data/umi.svg",
                Language("en", "English"),
                "United States Minor Outlying Islands",
                "NA"
            )
        )

        // Use MockK to mock the body() call and return the mockCountries
//        coEvery { mockHttpClient.get(any<String>()).body<List<Country>>() } returns mockCountries

//        // Mock the HttpClient's get and receive methods
//        coEvery { mockHttpClient.get(any<String>()) } returns mockk {
//            coEvery { body()<List<Country>>() } returns mockCountries
//        }

        // Initialize the repository with the mocked HttpClient
        val repository = CountryRepository()

        // Call the fetchCountryData function and assert the results
        val result = repository.fetchCountryData()

        // failing this test explicity because it is not yet complete.
        assertNotNull(null)

        assertNotNull(result)
        assertEquals(2, result?.size)
        assertEquals("Virgin Islands (U.S.)", result?.get(0)?.name)
    }
}