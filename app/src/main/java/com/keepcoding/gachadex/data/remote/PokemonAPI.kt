package com.keepcoding.gachadex.data.remote

import com.keepcoding.gachadex.data.dto.PokemonDTO
import com.keepcoding.gachadex.data.dto.PokemonSpeciesDTO
import com.keepcoding.gachadex.data.dto.SearchDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PokemonAPI {

    @GET("pokemon-species/{dexN}")
    suspend fun getPokemonSpecies(@Path("dexN") dexN: Int): PokemonSpeciesDTO

    @GET("pokemon-species/{name}")
    suspend fun getPokemonSpecies(@Path("name") name: String): PokemonSpeciesDTO

    @GET("pokemon-species/{region}/{dexN}")
    suspend fun getPokemonSpeciesRegionalDex(@Path("region") region: String, @Path("dexN") dexN: Int): PokemonSpeciesDTO

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): PokemonDTO

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): PokemonDTO

}