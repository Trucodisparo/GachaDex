package com.keepcoding.gachadex.domain.usecase

import com.keepcoding.gachadex.data.PokedexStatusRepository

class GetCurrentSettingsUseCase (
    private val repository: PokedexStatusRepository
) {
    suspend fun invoke() = repository.getCurrentSettings()
}