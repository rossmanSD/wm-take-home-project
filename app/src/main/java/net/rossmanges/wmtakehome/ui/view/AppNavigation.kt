package net.rossmanges.wmtakehome.ui.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import net.rossmanges.wmtakehome.data.Country

@Serializable
sealed class Routes {
    @Serializable
    data object CountryList : Routes()

    @Serializable
    data class CountryDetail(val countryCode: String) : Routes()
}

@Composable
fun CountryApp(countries: List<Country>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.CountryList) {
        composable<Routes.CountryList> {
            CountryList(countries = countries) {
                navController.navigate(Routes.CountryDetail(it))
            }
        }
        composable<Routes.CountryDetail> { backStackEntry ->
            val countryCode = backStackEntry.toRoute<Routes.CountryDetail>().countryCode
            val country = countries.firstOrNull { it.code == countryCode }
            if (country != null) {
                CountryDetailCard(country) {
                    navController.navigate(Routes.CountryList)
                }
            }
        }
    }
}