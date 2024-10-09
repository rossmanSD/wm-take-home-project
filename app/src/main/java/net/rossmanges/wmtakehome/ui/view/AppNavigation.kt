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
import net.rossmanges.wmtakehome.domain.model.ListItem

@Serializable
sealed class Routes {
    @Serializable
    data object CountryList : Routes()

    @Serializable
    data class CountryDetail(val countryCode: String) : Routes()
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CountryApp(listItems: List<ListItem>, lazyListState: LazyListState) {
    val navController = rememberNavController()
    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = Routes.CountryList) {
            composable<Routes.CountryList> {
                ItemList(
                    lazyListState = lazyListState,
                    listItems = listItems,
                    animatedVisibilityScope = this@composable,
                    sharedTransitionScope = this@SharedTransitionLayout
                ) {
                    navController.navigate(Routes.CountryDetail(it))
                }
            }
            composable<Routes.CountryDetail> { backStackEntry ->
                val countryCode = backStackEntry.toRoute<Routes.CountryDetail>().countryCode
                val countryItem = listItems
                    .filterIsInstance<ListItem.CountryListItem>()
                    .firstOrNull { it.country.code == countryCode }
                if (countryItem != null) {
                    CountryDetailCard(
                        country = countryItem.country,
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