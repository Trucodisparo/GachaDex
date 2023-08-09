package com.keepcoding.gachadex.data.dto.pokemon

import com.keepcoding.gachadex.data.dto.common.NameDTO
import com.squareup.moshi.Json

data class MovesDTO(
    @Json(name = "move") val move: NameDTO,
    @Json(name = "version_group_details") val details: List<MoveDetailsDTO>
)

data class MoveDetailsDTO(
    @Json(name = "level_learned_at") val level: Int,
    @Json(name = "move_learn_method") val method: NameDTO
)