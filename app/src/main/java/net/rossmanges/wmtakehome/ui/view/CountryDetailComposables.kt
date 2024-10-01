@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalSharedTransitionApi::class)

package net.rossmanges.wmtakehome.ui.view

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import net.rossmanges.wmtakehome.R
import net.rossmanges.wmtakehome.data.Country

/**
 * A container for presenting detailed data about a country in a pleasing format.
 * @param   country The [Country] data class.
 * @param   onClick The callback when the country detail called is tapped.
 */
@Composable
fun CountryDetailCard(
    country: Country,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    onClick: (String) -> Unit
) {
    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .fillMaxSize()
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
            BoxWithConstraints {
                Log.v("constraints", "maxWidth=$maxWidth")
                if (maxWidth > 400.dp) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CountryAndCode(country, animatedVisibilityScope)
                            Flag(country, animatedVisibilityScope)
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CountryShape(country)
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CountryAndCode(country, animatedVisibilityScope)
                        Flag(country, animatedVisibilityScope)
                    }
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CountryShape(country)
                    }
                }
            }
        }
    }
}

@Composable
fun CountryShape(country: Country) {
    AsyncImage(
        // note: lifted this country-shape endpoint from Wordle, need to find a replacement.
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://teuteuf-dashboard-assets.pages.dev/data/common/country-shapes/${country.code.lowercase()}.svg")
            .decoderFactory(SvgDecoder.Factory())
            .crossfade(true)
            .build(),
        contentDescription = "border map",
        modifier = Modifier
            .width(300.dp),
        contentScale = ContentScale.Fit,
        error = painterResource(id = R.drawable.flag_error_placeholder)
    )
}


