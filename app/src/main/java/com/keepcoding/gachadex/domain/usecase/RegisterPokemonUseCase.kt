package com.keepcoding.gachadex.domain.usecase

import com.keepcoding.gachadex.data.PokemonRepository
import com.keepcoding.gachadex.domain.model.PokedexEntryModel

class RegisterPokemonUseCase(
    private val repository: PokemonRepository
) {
    suspend fun invoke(pokemon: PokedexEntryModel) = repository.registerPokemon(pokemon)
}