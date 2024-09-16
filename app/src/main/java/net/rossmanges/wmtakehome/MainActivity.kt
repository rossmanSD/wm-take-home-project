package net.rossmanges.wmtakehome

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
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
        registerForNetworkConnectivityCallbacks()
    }

    /**
     * Register for network connectivity callbacks
     */
    private fun registerForNetworkConnectivityCallbacks() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    /**
     * Callback object to handle network availability changes. Note, this is a candidate for
     * extracting out of this Activity and into another part of the architecture to keep things
     * clean.
     */
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val hasInternet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            val hasValidatedInternet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            val hasUnmeteredInternet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
            Log.v(
                "network",
                "onCapabilitiesChanged() hasInternet=$hasInternet, hasValidatedInternet=$hasValidatedInternet, hasUnmeteredInternet=$hasUnmeteredInternet"
            )
            // attempt to reload country data if the original fetch was unsuccessful
            if (hasValidatedInternet && countryViewModel.countries.value.isEmpty()) {
                countryViewModel.loadCountries()
            }
        }
    }
}