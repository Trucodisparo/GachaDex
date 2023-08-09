package com.keepcoding.gachadex.domain.usecase

import com.keepcoding.gachadex.data.PokemonRepository
import com.keepcoding.gachadex.domain.model.PokedexEntryModel
import kotlinx.coroutines.flow.flow


class GetPokedexEntriesUseCase(
    private val repository: PokemonRepository
) {
    suspend fun invoke(region: String) = flow{emit(repository.getPokedexEntries(region))}
}