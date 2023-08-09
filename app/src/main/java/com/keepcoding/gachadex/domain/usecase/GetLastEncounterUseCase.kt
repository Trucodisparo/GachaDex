package com.keepcoding.gachadex.domain.usecase

import com.keepcoding.gachadex.data.SettingsRepository

class GetLastEncounterUseCase (
    private val repository: SettingsRepository
) {
    suspend fun invoke() = repository.getLastEncounter()
}