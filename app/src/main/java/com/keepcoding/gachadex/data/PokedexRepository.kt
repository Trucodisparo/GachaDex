package com.keepcoding.gachadex.data

import com.keepcoding.gachadex.common.DexConfig
import com.keepcoding.gachadex.domain.model.SettingsModel
import kotlinx.coroutines.flow.Flow

interface PokedexStatusRepository {
    suspend fun addDex(dexConfig: DexConfig)
    suspend fun clearSettings()
    suspend fun getCurrentSettings(): Flow<SettingsModel>
    suspend fun setSettings(newSettings: SettingsModel)
    suspend fun setLastEncounter()
    suspend fun getLastEncounter() : Flow<Long>
}