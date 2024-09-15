package net.rossmanges.wmtakehome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import net.rossmanges.wmtakehome.ui.theme.RossWalmartTakehomeProjectTheme
import net.rossmanges.wmtakehome.ui.view.MainScreen
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
                MainScreen(filteredCountries) { filterText ->
                    countryViewModel.updateFilter(filterText)
                }
            }
        }
    }
}