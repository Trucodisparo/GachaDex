package com.keepcoding.gachadex.domain.usecase

import com.keepcoding.gachadex.data.PokedexStatusRepository
import com.keepcoding.gachadex.domain.model.SettingsModel

class SetCurrentSettingsUseCase(
    private val repository: PokedexStatusRepository
) {
    suspend fun invoke(settings: SettingsModel) = repository.setSettings(settings)
}