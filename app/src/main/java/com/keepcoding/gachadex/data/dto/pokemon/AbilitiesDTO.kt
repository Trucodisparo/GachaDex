package com.keepcoding.gachadex.data.dto.pokemon

import com.keepcoding.gachadex.data.dto.common.NameDTO
import com.squareup.moshi.Json

data class AbilitiesDTO(
    @Json(name = "ability") val ability: NameDTO,
    @Json(name = "slot") val slot: Int,
    @Json(name = "is_hidden") val isHidden: Boolean
)