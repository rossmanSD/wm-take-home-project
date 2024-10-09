package net.rossmanges.wmtakehome.domain.model

import net.rossmanges.wmtakehome.data.Country

sealed interface ListItem {
    data class CountryListItem(val country: Country) : ListItem
    data class HeaderListItem(val letter: Char) : ListItem
}