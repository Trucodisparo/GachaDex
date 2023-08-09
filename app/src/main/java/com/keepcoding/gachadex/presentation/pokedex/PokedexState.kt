package com.keepcoding.gachadex.presentation.pokedex

import com.keepcoding.gachadex.common.DexConfig
import com.keepcoding.gachadex.domain.model.PokedexEntryModel

data class PokedexState(
    var isLoaded: Boolean = false,
    var list: List<PokedexEntryModel> = emptyList(),
    var currentRegion: String = "national",
    var dexCompletion: String = "${list.size}/${DexConfig.getDexByRegion(currentRegion).last}",
    var isError: Boolean = false,
    var errorMsg: String? = null
)