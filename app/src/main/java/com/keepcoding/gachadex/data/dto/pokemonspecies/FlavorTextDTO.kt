package com.keepcoding.gachadex.data.dto.pokemonspecies

import com.keepcoding.gachadex.data.dto.common.LanguageDTO
import com.keepcoding.gachadex.data.dto.common.NameDTO
import com.squareup.moshi.Json

data class FlavorTextDTO(
    @Json(name = "flavor_text") val text: String,
    @Json(name = "language") val language: LanguageDTO,
    @Json(name = "version") val version: NameDTO
)
