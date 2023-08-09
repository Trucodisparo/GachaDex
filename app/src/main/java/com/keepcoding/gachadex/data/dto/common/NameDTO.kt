package com.keepcoding.gachadex.data.dto.common

import com.squareup.moshi.Json

data class NameDTO(
    @Json(name = "name") val name: String
)
