package com.keepcoding.gachadex.data.dto.pokemonspecies

import com.keepcoding.gachadex.data.dto.common.NameDTO
import com.squareup.moshi.Json

data class PokedexDTO(
    @Json(name = "entry_number") val number: Int,
    @Json(name = "pokedex") val pokedex: NameDTO
)
