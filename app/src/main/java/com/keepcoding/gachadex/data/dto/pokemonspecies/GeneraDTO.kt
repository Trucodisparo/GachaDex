package com.keepcoding.gachadex.data.dto.pokemonspecies

import com.keepcoding.gachadex.data.dto.common.LanguageDTO
import com.squareup.moshi.Json

data class GeneraDTO (
    @Json(name = "genus") val genus: String,
    @Json(name = "language") val language: LanguageDTO
)