package com.keepcoding.gachadex.presentation.pokedex.details

import com.keepcoding.gachadex.common.DexConfig
import com.keepcoding.gachadex.domain.model.PokedexEntryModel
import com.keepcoding.gachadex.domain.model.PokemonModel

data class DetailsState(
    var isLoaded: Boolean = false,
    var pokemon: PokemonModel? = null,
    var isError: Boolean = false,
    var errorMsg: String? = null
)