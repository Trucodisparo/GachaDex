package com.keepcoding.androidsuperpoderes.di

import com.keepcoding.gachadex.presentation.encounter.EncounterViewModel
import com.keepcoding.gachadex.presentation.mainmenu.MainMenuViewModel
import com.keepcoding.gachadex.presentation.pokedex.PokedexViewModel
import com.keepcoding.gachadex.presentation.pokedex.details.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module{
    viewModel{PokedexViewModel(get(), get(), get(), get())}
    viewModel{EncounterViewModel(get(), get(), get())}
    viewModel{DetailsViewModel(get())}
    viewModel{MainMenuViewModel(get(), get())}
}