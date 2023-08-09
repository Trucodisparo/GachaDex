package com.keepcoding.gachadex.data.dto.pokemonspecies

import com.keepcoding.gachadex.data.dto.common.LanguageDTO
import com.squareup.moshi.Json

data class NamesDTO(
    @Json(name = "name") val name: String,
    @Json(name = "language") val language: LanguageDTO
)
