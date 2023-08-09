package com.keepcoding.gachadex.domain.usecase

import com.keepcoding.gachadex.data.SettingsRepository
import com.keepcoding.gachadex.domain.model.SettingsModel

class SetCurrentSettingsUseCase(
    private val repository: SettingsRepository
) {
    suspend fun invoke(settings: SettingsModel) = repository.setSettings(settings)
}