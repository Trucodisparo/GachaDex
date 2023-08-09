package com.keepcoding.gachadex.data

import com.keepcoding.gachadex.domain.model.PokedexEntryModel
import com.keepcoding.gachadex.domain.model.PokemonModel


interface PokemonRepository {
    suspend fun getPokedexEntries(region: String = "national"): List<PokedexEntryModel>
    suspend fun getPokemonDetails(id: Int): PokemonModel
    suspend fun getRandomPokemon(region: String = "national"): Pair<PokedexEntryModel, Boolean>
    suspend fun registerPokemon(pokemon: PokedexEntryModel)
    suspend fun clearPokedex()
}