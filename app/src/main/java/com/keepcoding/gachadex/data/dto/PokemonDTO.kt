package com.keepcoding.gachadex.data.dto

import com.keepcoding.gachadex.data.dto.common.NameDTO
import com.keepcoding.gachadex.data.dto.pokemon.AbilitiesDTO
import com.keepcoding.gachadex.data.dto.pokemon.MovesDTO
import com.keepcoding.gachadex.data.dto.pokemon.PictureDTO
import com.keepcoding.gachadex.data.dto.pokemon.StatDTO
import com.keepcoding.gachadex.data.dto.pokemon.TypeDTO
import com.squareup.moshi.Json

data class PokemonDTO (
    @Json(name = "id") val id: Int,
    @Json(name = "sprites") val picture: PictureDTO,
    @Json(name = "abilities") val abilities: List<AbilitiesDTO>,
    @Json(name = "types") val types: List<TypeDTO>,
    @Json(name = "moves") val moves: List<MovesDTO>,
    @Json(name = "species") val species: NameDTO,
    @Json(name = "stats") val stats: List<StatDTO>
)