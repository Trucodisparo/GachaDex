package com.keepcoding.gachadex.domain.usecase

import com.keepcoding.gachadex.data.PokemonRepository

class ResetPokedexUseCase (
    private val repository: PokemonRepository
) {
    suspend fun invoke() = repository.clearPokedex()
}