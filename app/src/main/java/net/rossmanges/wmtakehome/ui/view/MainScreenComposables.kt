package net.rossmanges.wmtakehome.ui.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.rossmanges.wmtakehome.domain.model.ListItem

/**
 * The Composable function that describes the main scaffold and layout for this app.
 */
@Composable
fun MainScreen(
    listItems: List<ListItem>,
    lazyListState: LazyListState,
    onSearchTextChanged: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBarWithSearch(
                onSearchTextChanged = { onSearchTextChanged(it) }
            )
        },
        content = { padding ->
            Surface(
                modifier = Modifier
                    .padding(
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding(),
                        start = 2.dp,
                        end = 7.dp
                    )
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                if (listItems.isEmpty()) {
                    NoDataMessage()
                } else {
                    CountryApp(listItems = listItems, lazyListState = lazyListState)
                }
            }
        }
    )
}