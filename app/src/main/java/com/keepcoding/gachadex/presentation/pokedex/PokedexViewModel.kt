package com.keepcoding.gachadex.presentation.pokedex

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keepcoding.gachadex.common.DexConfig
import com.keepcoding.gachadex.domain.model.PokemonModel
import com.keepcoding.gachadex.domain.model.SettingsModel
import com.keepcoding.gachadex.domain.usecase.GetCurrentSettingsUseCase
import com.keepcoding.gachadex.domain.usecase.GetLastEncounterUseCase
import com.keepcoding.gachadex.domain.usecase.GetPokedexEntriesUseCase
import com.keepcoding.gachadex.domain.usecase.ResetPokedexUseCase
import com.keepcoding.gachadex.domain.usecase.SetCurrentSettingsUseCase
import com.keepcoding.gachadex.domain.usecase.SetLastEncounterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokedexViewModel(
    private val getPokedexEntriesUseCase: GetPokedexEntriesUseCase,
    private val getCurrentSettingsUseCase: GetCurrentSettingsUseCase,
    private val setCurrentSettingsUseCase: SetCurrentSettingsUseCase,
    private val resetPokedexUseCase: ResetPokedexUseCase
): ViewModel() {
    private var _pokedex = MutableStateFlow(
        PokedexState(
            isLoaded = false,
            list = emptyList(),
        )
    )
    val pokedex: StateFlow<PokedexState> get() = _pokedex

    private var _settings = MutableStateFlow(SettingsModel(
        current_dex = DexConfig.NatDex,
        last_unlocked = DexConfig.KantoDex
    ))

    private val settings: StateFlow<SettingsModel> get() = _settings

    init{
        fetchSettings()
    }

    fun getData(region: String = settings.value.current_dex.region){
        viewModelScope.launch{
            try {
                withContext(Dispatchers.IO) {
                    if(region != settings.value.current_dex.region) {
                        val newSettings = SettingsModel(
                            DexConfig.getDexByRegion(region),
                            settings.value.last_unlocked
                        )
                        setCurrentSettingsUseCase.invoke(newSettings)
                        _settings.value = newSettings
                    }
                    getPokedexEntriesUseCase.invoke(region).collect{
                        _pokedex.value = PokedexState(isLoaded = true, currentRegion = region, list = it)
                    }
                    if(region == settings.value.last_unlocked.region && isDexComplete()) unlockNextDex()
                }
            }catch(t: Throwable){
                _pokedex.value = PokedexState(isError = true, errorMsg = t.toString())
                Log.d("LIST_ERROR", t.toString())
            }
        }
    }

    fun getAvailableRegions() : List<String>{
        val last_unlock_index = DexConfig.regions.indexOf(settings.value.last_unlocked.region)
        return DexConfig.regions.filter{
            DexConfig.regions.indexOf(it) <= last_unlock_index
        }.map{it.replaceFirstChar { char -> char.uppercase() }}
    }

    private fun fetchSettings(){
        viewModelScope.launch{
            withContext(Dispatchers.IO) {
                getCurrentSettingsUseCase.invoke().collectLatest{
                    _settings.value = it
                    return@collectLatest
                }
            }
        }
    }

    private fun isDexComplete(): Boolean{
        return (pokedex.value.list.size == DexConfig.getDexByRegion(settings.value.last_unlocked.region).last)
    }

     fun unlockNextDex(){
        val last_unlock_index = DexConfig.regions.indexOf(settings.value.last_unlocked.region)
         viewModelScope.launch {
             withContext(Dispatchers.IO) {
                 setCurrentSettingsUseCase.invoke(SettingsModel(
                     current_dex = settings.value.current_dex,
                     last_unlocked = DexConfig.getDexByRegion(DexConfig.regions[last_unlock_index+1])
                 ))
             }
         }
    }

    fun resetDex() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                resetPokedexUseCase.invoke()
            }
        }
    }
}