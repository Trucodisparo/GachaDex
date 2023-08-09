package com.keepcoding.gachadex.di

import com.keepcoding.gachadex.domain.usecase.GetCurrentSettingsUseCase
import com.keepcoding.gachadex.domain.usecase.GetLastEncounterUseCase
import com.keepcoding.gachadex.domain.usecase.GetPokedexEntriesUseCase
import com.keepcoding.gachadex.domain.usecase.GetPokemonDetailsUseCase
import com.keepcoding.gachadex.domain.usecase.GetRandomEncounterUseCase
import com.keepcoding.gachadex.domain.usecase.RegisterPokemonUseCase
import com.keepcoding.gachadex.domain.usecase.ResetPokedexUseCase
import com.keepcoding.gachadex.domain.usecase.SetCurrentSettingsUseCase
import com.keepcoding.gachadex.domain.usecase.SetLastEncounterUseCase
import org.koin.dsl.module

val domainModule  = module {
    single{GetPokedexEntriesUseCase(get())}
    single{GetRandomEncounterUseCase(get())}
    single{RegisterPokemonUseCase(get())}
    single{GetCurrentSettingsUseCase(get())}
    single{SetCurrentSettingsUseCase(get())}
    single{GetPokemonDetailsUseCase(get())}
    single{GetLastEncounterUseCase(get())}
    single{SetLastEncounterUseCase(get())}
    single{ResetPokedexUseCase(get(), get())}
}