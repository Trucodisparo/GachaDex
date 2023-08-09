package com.keepcoding.gachadex.data.remote

import com.keepcoding.gachadex.data.dto.PokemonDTO
import com.keepcoding.gachadex.data.dto.PokemonSpeciesDTO

interface RemoteDataSource {

    suspend fun getPokemon(id: Int): PokemonDTO
    suspend fun getPokemon(name: String): PokemonDTO

    suspend fun getPokemonSpecies(dexN: Int): PokemonSpeciesDTO
    suspend fun getPokemonSpecies(name: String): PokemonSpeciesDTO
    suspend fun getPokemonSpeciesRegionalDex(region: String, dexN: Int) : PokemonSpeciesDTO
}
