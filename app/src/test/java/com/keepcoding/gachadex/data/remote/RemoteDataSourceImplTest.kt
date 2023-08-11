package com.keepcoding.gachadex.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.keepcoding.gachadex.data.dto.PokemonDTO
import com.keepcoding.gachadex.data.dto.PokemonSpeciesDTO
import com.keepcoding.gachadex.data.dto.common.NameDTO
import com.keepcoding.gachadex.data.dto.pokemon.ArtworkDTO
import com.keepcoding.gachadex.data.dto.pokemon.OfficialArtworkDTO
import com.keepcoding.gachadex.data.dto.pokemon.PictureDTO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.DefaultDispatcherRule

internal class RemoteDataSourceImplTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val defaultDispatcherRule = DefaultDispatcherRule()

    @MockK(relaxed = true)
    private lateinit var api: PokemonAPI

    @Before
    fun setup(){
        MockKAnnotations.init(this)
    }

    private val pokemonDTO = PokemonDTO(
        id = 1,
        picture = PictureDTO(ArtworkDTO(OfficialArtworkDTO(""))),
        abilities = emptyList(),
        types = emptyList(),
        moves = emptyList(),
        species = NameDTO(""),
        stats = emptyList()
    )

    private val pokemonSpeciesDTO = PokemonSpeciesDTO(
        eggGroups = emptyList(),
        captureRate = 0,
        descriptor = emptyList(),
        names = emptyList(),
        pokedexNumbers = emptyList(),
        pokedexEntries = emptyList(),
        varieties = emptyList()
    )

    @Test
    fun testGetPokemonByID() = runTest{
        val id = 1
        coEvery { api.getPokemon(id) } returns
                pokemonDTO

        val src = RemoteDataSourceImpl(api)
        assertEquals(src.getPokemon(id), pokemonDTO)
    }

    @Test
    fun testGetPokemonByName() = runTest{
        val name = "Bulbasaur"
        coEvery { api.getPokemon(name) } returns
                pokemonDTO

        val src = RemoteDataSourceImpl(api)
        assertEquals(src.getPokemon(name), pokemonDTO)
    }

    @Test
    fun testGetPokemonSpeciesByID() = runTest{
        val id = 1
        coEvery { api.getPokemonSpecies(id) } returns
                pokemonSpeciesDTO

        val src = RemoteDataSourceImpl(api)
        assertEquals(src.getPokemonSpecies(id), pokemonSpeciesDTO)
    }

    @Test
    fun testGetPokemonSpeciesByName() = runTest{
        val name = "Bulbasaur"
        coEvery { api.getPokemonSpecies(name) } returns
                pokemonSpeciesDTO

        val src = RemoteDataSourceImpl(api)
        assertEquals(src.getPokemonSpecies(name), pokemonSpeciesDTO)
    }
}