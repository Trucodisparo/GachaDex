package com.keepcoding.gachadex.domain.usecase

import com.keepcoding.gachadex.data.PokemonRepository
import com.keepcoding.gachadex.data.PokedexStatusRepository

class ResetPokedexUseCase (
    private val pokemonRepo: PokemonRepository,
    private val settingsRepo: PokedexStatusRepository
) {
    suspend fun invoke() {
        pokemonRepo.clearPokedex()
        settingsRepo.clearSettings()
    }
}