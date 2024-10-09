package net.rossmanges.wmtakehome.ui.viewmodel

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.rossmanges.wmtakehome.data.Country
import net.rossmanges.wmtakehome.domain.model.ListItem
import net.rossmanges.wmtakehome.repository.CountryRepository

/**
 * ViewModel for handling the display of Country data.
 * @param repository    The [CountryRepository].
 */
class CountryViewModel(private val repository: CountryRepository) : ViewModel() {
    private val _filterText = MutableStateFlow("")
    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val lazyListState: LazyListState by mutableStateOf( LazyListState(0,0))

    /**
     * The unfiltered list of [Country] data.
     */
    val countries: StateFlow<List<Country>>
        get() = _countries

    /**
     * Filtered list of countries based on the filter text. Provides data in the form of
     * a List of [ListItem].
     */
    val filteredCountries: StateFlow<List<ListItem>> =
        combine(_countries, _filterText) { countries, filter ->
            if (filter.isEmpty()) {
                getHeadersAndCountries(countries) // No filter applied
            } else {
                getHeadersAndCountries(
                    countries.filter { country ->
                        country.name.contains(filter, ignoreCase = true) ||
                                country.region?.contains(filter, ignoreCase = true) ?: false ||
                                country.code.contains(filter, ignoreCase = true)
                    })
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    init {
        loadCountries()
    }

    private fun getHeadersAndCountries(countries: List<Country>): List<ListItem> {
        val headersAndCountries = mutableListOf<ListItem>()
        val alphaGroup = countries.sortedBy { it.name }.groupBy { it.name.first() }
        alphaGroup.entries.forEach { (header, countries) ->
            headersAndCountries.add(ListItem.HeaderListItem(header))
            headersAndCountries.addAll(countries.map { country ->
                ListItem.CountryListItem(country)
            })
        }
        return headersAndCountries
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