package com.keepcoding.gachadex.presentation.encounter

import com.keepcoding.gachadex.domain.model.PokedexEntryModel

data class EncounterState(
    var isLoaded: Boolean = false,
    var pokemon: PokedexEntryModel? = null,
    var isRegistered: Boolean = false,
    var isError: Boolean = false,
    var errorMsg: String? = null
)