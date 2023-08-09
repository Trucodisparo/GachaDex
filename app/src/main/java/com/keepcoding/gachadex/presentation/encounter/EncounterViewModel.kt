package com.keepcoding.gachadex.presentation.encounter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keepcoding.gachadex.common.DexConfig
import com.keepcoding.gachadex.domain.model.SettingsModel
import com.keepcoding.gachadex.domain.usecase.GetCurrentSettingsUseCase
import com.keepcoding.gachadex.domain.usecase.GetPokedexEntriesUseCase
import com.keepcoding.gachadex.domain.usecase.GetRandomEncounterUseCase
import com.keepcoding.gachadex.domain.usecase.RegisterPokemonUseCase
import com.keepcoding.gachadex.presentation.pokedex.PokedexState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EncounterViewModel(
    private val getRandomEncounterUseCase: GetRandomEncounterUseCase,
    private val registerPokemonUseCase: RegisterPokemonUseCase,
    private val getCurrentSettingsUseCase: GetCurrentSettingsUseCase
): ViewModel() {
    private var _encounter = MutableStateFlow(EncounterState())
    val encounter: StateFlow<EncounterState> get() = _encounter

    private var _settings = MutableStateFlow(
        SettingsModel(
        current_dex = DexConfig.NatDex,
        last_unlocked = DexConfig.KantoDex
    )
    )

    private val settings: StateFlow<SettingsModel> get() = _settings

    init{
        fetchSettings()
    }

    fun getRandomEncounter(){
        if(encounter.value.pokemon == null) {
            viewModelScope.launch {
                try {
                    withContext(Dispatchers.IO) {
                        fetchSettings()
                        getRandomEncounterUseCase.invoke(settings.value.last_unlocked.region).collect {
                            _encounter.value = EncounterState(pokemon = it.first, isRegistered = it.second, isLoaded = true)
                        }
                    }
                } catch (t: Throwable) {
                    _encounter.value = EncounterState(isError = true, errorMsg = t.toString())
                    Log.d("LIST_ERROR", t.toString())
                }
            }
        }
    }

    private fun fetchSettings(){
        viewModelScope.launch{
            try {
                withContext(Dispatchers.IO) {
                    getCurrentSettingsUseCase.invoke().collect{
                        _settings.value = it
                    }
                }
            }catch(t: Throwable){
                Log.d("SETTINGS_ERROR", t.toString())
            }
        }
    }

    fun registerRandomEncounter(){
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    encounter.value.pokemon?.let {
                        registerPokemonUseCase.invoke(it)
                    }
                    _encounter.value = EncounterState(
                        isLoaded = encounter.value.isLoaded,
                        pokemon = encounter.value.pokemon,
                        isRegistered = true
                    )
                }
            } catch (t: Throwable) {
                _encounter.value = EncounterState(isError = true, errorMsg = t.toString())
                Log.d("LIST_ERROR", t.toString())
            }
        }
    }

    fun clearEncounter(){
        _encounter.value = EncounterState(pokemon = null, isRegistered = false, isLoaded = false)
    }
}