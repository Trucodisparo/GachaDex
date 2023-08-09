package com.keepcoding.gachadex.domain.usecase

import com.keepcoding.gachadex.data.PokemonRepository
import com.keepcoding.gachadex.data.SettingsRepository

class ResetPokedexUseCase (
    private val pokemonRepo: PokemonRepository,
    private val settingsRepo: SettingsRepository
) {
    suspend fun invoke() {
        pokemonRepo.clearPokedex()
        settingsRepo.clearSettings()
    }
}