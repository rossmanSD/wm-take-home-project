@file:OptIn(ExperimentalSharedTransitionApi::class)

package net.rossmanges.wmtakehome.ui.view

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import net.rossmanges.wmtakehome.R
import net.rossmanges.wmtakehome.data.Country
import net.rossmanges.wmtakehome.ui.theme.RossWmtTypography


/**
 * A container for presenting data about a country in a pleasing format.
 * @param   country The [Country] data class.
 */
@Composable
fun CountryCard(
    country: Country,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    onClick: (String) -> Unit
) {
    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(key = "card-${country.code}"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .clickable { onClick(country.code) },
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CountryAndCode(country, animatedVisibilityScope)

                Row {
                    CountryMetadata(country, animatedVisibilityScope)
                    Spacer(Modifier.weight(1f))
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://flagcdn.com/w320/${country.code.lowercase()}.jpg")
                            .crossfade(true)
                            .build(),
                        contentDescription = "Country flag",
                        modifier = Modifier
                            .width(80.dp)
                            .align(Alignment.Bottom)
                            .clip(RoundedCornerShape(3.dp))
                            .sharedElement(
                                state = rememberSharedContentState(key = country.code),
                                animatedVisibilityScope = animatedVisibilityScope
                            ),
                        contentScale = ContentScale.Fit,
                        placeholder = painterResource(id = R.drawable.flag_placeholder),
                        error = painterResource(id = R.drawable.flag_error_placeholder)
                    )
                }
            }
        }
    }
}

/**
 * A "lazy" column of [CountryCard]
 * @param countries The list of [Country] data to use in the list.
 */
@Composable
fun CountryList(
    countries: List<Country>,
    lazyListState: LazyListState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    onClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
    ) {
        items(countries) { country ->
            CountryCard(
                country = country,
                animatedVisibilityScope = animatedVisibilityScope,
                sharedTransitionScope = sharedTransitionScope
            ) {
                onClick(it)
            }
        }
    }
}

/**
 * Displays a message centered on the screen that no data is available.
 */
@Composable
fun NoDataMessage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No Data Available",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
internal fun SharedTransitionScope.CountryAndCode(
    country: Country,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Row {
        Text(
            modifier = Modifier.sharedBounds(
                sharedContentState = rememberSharedContentState(key = "text-country-${country.code}"),
                animatedVisibilityScope = animatedVisibilityScope
            ),
            text = "${country.name}, ${country.region}",
            style = RossWmtTypography.headlineSmall
        )
        Spacer(Modifier.weight(1f))
        Text(
            modifier = Modifier.sharedBounds(
                sharedContentState = rememberSharedContentState(key = "text-country-code-${country.code}"),
                animatedVisibilityScope = animatedVisibilityScope
            ),
            text = country.code,
            style = RossWmtTypography.headlineSmall
        )
    }
}

@Composable
internal fun SharedTransitionScope.CountryMetadata(
    country: Country,
    animatedVisibilityScope: AnimatedVisibilityScope) {
    Column {
        Text(
            modifier = Modifier.sharedBounds(
                sharedContentState = rememberSharedContentState(key = "text-country-cap-${country.code}"),
                animatedVisibilityScope = animatedVisibilityScope
            ),
            text = "Capital: ${country.capital}",
            style = RossWmtTypography.bodyMedium
        )
        val currencySymbol =
            if (country.currency.symbol == null) "" else ", (${country.currency.symbol})"
        Text(
            modifier = Modifier.sharedBounds(
                sharedContentState = rememberSharedContentState(key = "text-country-currency-${country.code}"),
                animatedVisibilityScope = animatedVisibilityScope
            ),
            text = "${country.currency.name}$currencySymbol",
            style = RossWmtTypography.bodyMedium
        )
        Text(
            modifier = Modifier.sharedBounds(
                sharedContentState = rememberSharedContentState(key = "text-country-language-${country.code}"),
                animatedVisibilityScope = animatedVisibilityScope
            ),
            text = "${country.language.name}",
            style = RossWmtTypography.bodyMedium
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun PreviewCountryCard() {
//    val sampleCountry = listOf(
//        Country(
//        capital = "Charlotte Amalie",
//        code = "VI",
//        currency = Currency(
//            code = "USD",
//            name = "United States dollar",
//            symbol = "$"
//        ),
//        flag = "https://restcountries.eu/data/vir.svg",
//        language = Language(
//            code = "en",
//            name = "English"
//        ),
//        name = "Virgin Islands (U.S.)",
//        region = "NA"
//    ),
//        Country(
//            capital = "Flying Fish Cove",
//            code = "CX",
//            currency = Currency(
//                code = "AUD",
//                name = "Australian dollar",
//                symbol = "$"
//            ),
//            flag = "https://restcountries.eu/data/cxr.svg",
//            language = Language(
//                code = "en",
//                name = "English"
//            ),
//            name = "Christmas Island",
//            region = "OC"
//        )
//    )
//    CountryList(countries = sampleCountry) { }
//}
