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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    // note: lifted this country-shape endpoint from Wordle, need to find a replacement.
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://teuteuf-dashboard-assets.pages.dev/data/common/country-shapes/${country.code.lowercase()}.svg")
                        .decoderFactory(SvgDecoder.Factory())
                        .crossfade(true)
                        .build(),
                    contentDescription = "border map",
                    modifier = Modifier
                        .width(300.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Fit,
                    error = painterResource(id = R.drawable.flag_error_placeholder)
                )
            }
        }
    }
}


