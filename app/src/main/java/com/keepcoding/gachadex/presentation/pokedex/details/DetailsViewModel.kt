package com.keepcoding.gachadex.presentation.pokedex.details

import android.telecom.Call.Details
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keepcoding.gachadex.common.DexConfig
import com.keepcoding.gachadex.domain.model.SettingsModel
import com.keepcoding.gachadex.domain.usecase.GetCurrentSettingsUseCase
import com.keepcoding.gachadex.domain.usecase.GetPokedexEntriesUseCase
import com.keepcoding.gachadex.domain.usecase.GetPokemonDetailsUseCase
import com.keepcoding.gachadex.domain.usecase.SetCurrentSettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase
): ViewModel() {
    private var _pokemon = MutableStateFlow(
        DetailsState()
    )
    val pokemon: StateFlow<DetailsState> get() = _pokemon

    fun getData(id: Int){
        viewModelScope.launch{
            try {
                withContext(Dispatchers.IO) {
                    getPokemonDetailsUseCase.invoke(id).collect{
                        _pokemon.value = DetailsState(isLoaded = true, pokemon = it)
                    }
                }
            }catch(t: Throwable){
                _pokemon.value = DetailsState(isError = true, errorMsg = t.toString())
                Log.d("LIST_ERROR", t.toString())
            }
        }
    }
}