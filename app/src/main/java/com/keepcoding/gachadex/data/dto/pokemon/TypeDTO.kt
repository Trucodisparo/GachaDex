package com.keepcoding.gachadex.data.dto.pokemon

import com.keepcoding.gachadex.data.dto.common.NameDTO
import com.squareup.moshi.Json

data class TypeDTO(
    @Json(name = "slot") val slot: Int,
    @Json(name = "type") val type: NameDTO
)