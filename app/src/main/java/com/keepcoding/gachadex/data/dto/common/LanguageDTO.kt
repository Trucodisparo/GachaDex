package com.keepcoding.gachadex.data.dto.common

import com.squareup.moshi.Json

data class LanguageDTO(
    @Json(name = "name") val name: String,
)