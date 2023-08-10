package com.keepcoding.gachadex.domain.usecase

import com.keepcoding.gachadex.data.PokedexStatusRepository
import com.keepcoding.gachadex.domain.model.PokedexStatusModel

class SetCurrentSettingsUseCase(
    private val repository: PokedexStatusRepository
) {
    suspend fun invoke(settings: PokedexStatusModel) = repository.setSettings(settings)
}