package net.rossmanges.wmtakehome.ui.view

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
import net.rossmanges.wmtakehome.domain.model.ListItem
import net.rossmanges.wmtakehome.ui.theme.RossWmtTypography

/**
 * A container for presenting data about a country in a pleasing format.
 * @param   country The [Country] data class.
 */
@Composable
fun CountryCard(country: Country) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
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
            Row {
                Text(
                    text = "${country.name}, ${country.region}",
                    style = RossWmtTypography.headlineSmall
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = country.code,
                    style = RossWmtTypography.headlineSmall
                )
            }

            Row {
                Column {
                    Text(
                        text = "Capital: ${country.capital}",
                        style = RossWmtTypography.bodyMedium
                    )
                    val currencySymbol = if (country.currency.symbol == null) "" else ", (${country.currency.symbol})"
                    Text(
                        text = "${country.currency.name}$currencySymbol",
                        style = RossWmtTypography.bodyMedium
                    )
                    Text(
                        text = "${country.language.name}",
                        style = RossWmtTypography.bodyMedium
                    )
                }
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
                        .clip(RoundedCornerShape(3.dp)),
                    contentScale = ContentScale.Fit,
                    placeholder = painterResource(id = R.drawable.flag_placeholder),
                    error = painterResource(id = R.drawable.flag_error_placeholder)
                )
            }
        }
    }
}

/**
 * A "lazy" column of [ListItem]
 * @param listItems The list of [ListItem] data to use in the list.
 */
@Composable
fun ItemList(listItems: List<ListItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(listItems) { item ->
            when (item) {
                is ListItem.HeaderListItem -> {
                    Row {
                        Text(
                            text = "${item.letter}",
                            style = RossWmtTypography.headlineLarge,
                            modifier = Modifier.padding(start = 12.dp, top = 8.dp)
                        )
                    }
                }

                is ListItem.CountryListItem -> {
                    CountryCard(country = item.country)
                }
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

// TODO - fix the preview code
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
//    ItemList(listItems = sampleCountry)
//}
