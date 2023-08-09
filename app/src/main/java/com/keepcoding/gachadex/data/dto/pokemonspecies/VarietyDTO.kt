package com.keepcoding.gachadex.data.dto.pokemonspecies

import com.keepcoding.gachadex.data.dto.common.NameDTO
import com.squareup.moshi.Json

data class VarietyDTO(
    @Json(name = "pokemon") val pokemon: NameDTO
)
