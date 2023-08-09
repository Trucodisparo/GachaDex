package com.keepcoding.gachadex.data.dto.pokemon

import com.keepcoding.gachadex.data.dto.common.NameDTO
import com.squareup.moshi.Json

data class StatDTO(
    @Json(name = "base_stat") val value: Int,
    @Json(name = "stat") val name: NameDTO,
    @Json(name = "effort") val effort_value: Int
)
