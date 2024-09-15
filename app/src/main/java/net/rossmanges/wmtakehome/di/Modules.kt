package net.rossmanges.wmtakehome.di

import net.rossmanges.wmtakehome.repository.CountryRepository
import net.rossmanges.wmtakehome.ui.viewmodel.CountryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<CountryRepository> { CountryRepository() }
    viewModel { CountryViewModel(get()) }
}