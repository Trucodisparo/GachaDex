package com.keepcoding.gachadex.domain.usecase

import com.keepcoding.gachadex.data.PokemonRepository
import kotlinx.coroutines.flow.flow

class GetPokemonDetailsUseCase(
    private val repository: PokemonRepository
) {
    suspend fun invoke(id: Int) = flow{emit(repository.getPokemonDetails(id))}
}