package net.rossmanges.wmtakehome.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.rossmanges.wmtakehome.data.Country
import net.rossmanges.wmtakehome.repository.CountryRepository

/**
 * ViewModel for handling the display of Country data.
 * @param repository    The [CountryRepository].
 */
class CountryViewModel(private val repository: CountryRepository) : ViewModel() {
    private val _filterText = MutableStateFlow("")
    private val _countries = MutableStateFlow<List<Country>>(emptyList())

    /**
     * The unfiltered list of [Country] data.
     */
    val countries: StateFlow<List<Country>> = _countries

    /**
     * Filtered list of [Country] data based on the filter text.
     */
    val filteredCountries: StateFlow<List<Country>> =
        combine(_countries, _filterText) { countries, filter ->
            if (filter.isEmpty()) {
                countries // No filter applied
            } else {
                countries.filter { country ->
                    country.name.contains(filter, ignoreCase = true) ||
                    country.region?.contains(filter, ignoreCase = true) ?: false ||
                    country.code.contains(filter, ignoreCase = true)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    init {
        loadCountries()
    }

    /**
     * Initiates fetching of country data from the [CountryRepository].
     */
    fun loadCountries() {
        viewModelScope.launch {
            val data = repository.fetchCountryData() ?: emptyList()
            _countries.value = data
        }
    }

    /**
     * Updates the filter text used to filter the list of [Country] data.
     * @param   newText The specified text to use for filtering.
     */
    fun updateFilter(newText: String) {
        _filterText.value = newText
    }
}