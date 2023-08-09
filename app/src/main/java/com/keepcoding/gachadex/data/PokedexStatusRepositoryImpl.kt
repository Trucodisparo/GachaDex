package com.keepcoding.gachadex.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.keepcoding.gachadex.common.DexConfig
import com.keepcoding.gachadex.domain.model.SettingsModel
import kotlinx.coroutines.flow.map

const val DATASTORE = "settings"

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = DATASTORE)

class SettingsRepositoryImpl(private val context: Context): PokedexStatusRepository {

    companion object{
        val current_dex = stringPreferencesKey("current_dex")
        val last_unlocked = stringPreferencesKey("last_unlocked")
        val last_encounter = longPreferencesKey("last_encounter")
    }

    override suspend fun addDex(dexConfig: DexConfig) {
        context.dataStore.edit{ settings ->
            settings[last_unlocked] = dexConfig.region
        }
    }

    override suspend fun getCurrentSettings() = context.dataStore.data.map{settings ->
        Log.d("SETTINGS_CURRENT", settings[last_unlocked].toString())
        SettingsModel(
            current_dex = DexConfig.getDexByRegion(settings[current_dex] ?: "kanto"),
            last_unlocked = DexConfig.getDexByRegion(settings[last_unlocked] ?: "kanto"),
        )
    }

    override suspend fun setSettings(newSettings: SettingsModel){
        context.dataStore.edit{settings ->
            settings[current_dex] = newSettings.current_dex.region
            settings[last_unlocked] = newSettings.last_unlocked.region
        }
    }

    override suspend fun clearSettings() {
        context.dataStore.edit { settings ->
            settings[current_dex] = DexConfig.KantoDex.region
            settings[last_unlocked] = DexConfig.KantoDex.region
            settings[last_encounter] = 0L
        }
    }

    override suspend fun setLastEncounter(){
        context.dataStore.edit { settings ->
            settings[last_encounter] = System.currentTimeMillis()
        }
    }

    override suspend fun getLastEncounter() = context.dataStore.data.map{settings -> settings[last_encounter] ?: 0L}
}