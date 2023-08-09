package com.keepcoding.gachadex.data.remote

import com.keepcoding.gachadex.data.dto.PokemonDTO
import com.keepcoding.gachadex.data.dto.PokemonSpeciesDTO

class RemoteDataSourceImpl(
    private val pokemonAPI: PokemonAPI
): RemoteDataSource {
    override suspend fun getPokemon(id: Int): PokemonDTO = pokemonAPI.getPokemon(id)
    override suspend fun getPokemon(name: String): PokemonDTO = pokemonAPI.getPokemon(name)
    override suspend fun getPokemonSpecies(dexN: Int): PokemonSpeciesDTO = pokemonAPI.getPokemonSpecies(dexN)
    override suspend fun getPokemonSpecies(name: String): PokemonSpeciesDTO = pokemonAPI.getPokemonSpecies(name)
    override suspend fun getPokemonSpeciesRegionalDex(region: String, dexN: Int) : PokemonSpeciesDTO = pokemonAPI.getPokemonSpeciesRegionalDex(region, dexN)
}