package com.keepcoding.gachadex.data.dto

import com.keepcoding.gachadex.data.dto.common.NameDTO
import com.keepcoding.gachadex.data.dto.pokemonspecies.FlavorTextDTO
import com.keepcoding.gachadex.data.dto.pokemonspecies.GeneraDTO
import com.keepcoding.gachadex.data.dto.pokemonspecies.NamesDTO
import com.keepcoding.gachadex.data.dto.pokemonspecies.PokedexDTO
import com.keepcoding.gachadex.data.dto.pokemonspecies.VarietyDTO
import com.squareup.moshi.Json

data class PokemonSpeciesDTO(
    @Json(name = "egg_groups") val eggGroups: List<NameDTO>,
    @Json(name = "capture_rate") val captureRate: Int,
    @Json(name = "genera") val descriptor: List<GeneraDTO>,
    @Json(name = "names") val names: List<NamesDTO>,
    @Json(name = "pokedex_numbers") val pokedexNumbers: List<PokedexDTO>,
    @Json(name = "flavor_text_entries") val pokedexEntries: List<FlavorTextDTO>,
    @Json(name = "varieties") val varieties: List<VarietyDTO>
)
