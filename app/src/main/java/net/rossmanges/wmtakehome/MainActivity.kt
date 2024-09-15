package net.rossmanges.wmtakehome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.rossmanges.wmtakehome.ui.theme.RossWalmartTakehomeProjectTheme
import net.rossmanges.wmtakehome.ui.view.CountryList
import net.rossmanges.wmtakehome.ui.view.NoDataMessage
import net.rossmanges.wmtakehome.ui.view.TopAppBarWithSearch
import net.rossmanges.wmtakehome.ui.viewmodel.CountryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val countryViewModel: CountryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val filteredCountries by countryViewModel.filteredCountries.collectAsState()
            RossWalmartTakehomeProjectTheme {
                Scaffold(
                    topBar = {
                        TopAppBarWithSearch(
                            onSearchTextChanged = { countryViewModel.updateFilter(it) }
                        )
                    },
                    content = { padding ->
                        Surface(
                            modifier = Modifier
                                .padding(top = padding.calculateTopPadding(), bottom = padding.calculateBottomPadding(), start = 2.dp, end=7.dp)
                                .fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            if (filteredCountries.isEmpty()) {
                                NoDataMessage()
                            } else {
                                CountryList(countries = filteredCountries)
                            }
                        }
                    }
                )
            }
        }
    }
}