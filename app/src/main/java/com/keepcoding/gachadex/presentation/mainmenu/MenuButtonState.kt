package com.keepcoding.gachadex.presentation.mainmenu

import com.keepcoding.gachadex.domain.model.PokedexEntryModel

data class MenuButtonState(
    val isEnabled: Boolean = true,
    val text: String = "Encounter",
)