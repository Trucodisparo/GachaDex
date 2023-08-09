package com.keepcoding.gachadex.domain.usecase

import com.keepcoding.gachadex.data.PokedexStatusRepository

class GetLastEncounterUseCase (
    private val repository: PokedexStatusRepository
) {
    suspend fun invoke() = repository.getLastEncounter()
}