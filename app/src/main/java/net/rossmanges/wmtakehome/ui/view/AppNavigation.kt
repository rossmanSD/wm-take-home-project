package net.rossmanges.wmtakehome.ui.view

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.lazy.LazyListState
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CountryApp(countries: List<Country>, lazyListState: LazyListState) {
    val navController = rememberNavController()
    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = Routes.CountryList) {
            composable<Routes.CountryList> {
                CountryList(
                    lazyListState = lazyListState,
                    countries = countries,
                    animatedVisibilityScope = this@composable,
                    sharedTransitionScope = this@SharedTransitionLayout
                ) {
                    navController.navigate(Routes.CountryDetail(it))
                }
            }
            composable<Routes.CountryDetail> { backStackEntry ->
                val countryCode = backStackEntry.toRoute<Routes.CountryDetail>().countryCode
                val country = countries.firstOrNull { it.code == countryCode }
                if (country != null) {
                    CountryDetailCard(
                        country = country,
                        animatedVisibilityScope = this@composable,
                        sharedTransitionScope = this@SharedTransitionLayout
                    ) {
                        navController.navigate(Routes.CountryList)
                    }
                }
            }
        }
    }
}