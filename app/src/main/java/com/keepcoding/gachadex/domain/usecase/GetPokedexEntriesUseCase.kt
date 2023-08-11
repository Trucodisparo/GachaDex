package com.keepcoding.gachadex.domain.usecase

import com.keepcoding.gachadex.data.PokemonRepository
import kotlinx.coroutines.flow.flow


class GetPokedexEntriesUseCase(
    private val repository: PokemonRepository
) {
    suspend fun invoke(region: String = "national") = flow{emit(repository.getPokedexEntries(region))}
}