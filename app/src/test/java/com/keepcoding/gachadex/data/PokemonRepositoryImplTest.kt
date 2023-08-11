package com.keepcoding.gachadex.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.keepcoding.gachadex.common.DexConfig
import com.keepcoding.gachadex.data.dto.PokemonDTO
import com.keepcoding.gachadex.data.dto.PokemonSpeciesDTO
import com.keepcoding.gachadex.data.dto.common.NameDTO
import com.keepcoding.gachadex.data.dto.pokemon.ArtworkDTO
import com.keepcoding.gachadex.data.dto.pokemon.OfficialArtworkDTO
import com.keepcoding.gachadex.data.dto.pokemon.PictureDTO
import com.keepcoding.gachadex.data.local.LocalDataSource
import com.keepcoding.gachadex.data.local.PokemonLocal
import com.keepcoding.gachadex.data.mappers.toPokedexEntryModel
import com.keepcoding.gachadex.data.remote.RemoteDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.DefaultDispatcherRule

internal class PokemonRepositoryImplTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val defaultDispatcherRule = DefaultDispatcherRule()

    @MockK(relaxed = true)
    private lateinit var remoteDataSource: RemoteDataSource
    @MockK(relaxed = true)
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setup(){
        MockKAnnotations.init(this)
    }
    private val list = List(150){ PokemonLocal(id = it+1,
        name = "Pokemon$it",
        title = "Pokemon n$it",
        dexNumber = it+1,
        dexNKanto = it+1,
    )}

    @Test
    fun testGetPokedexEntriesNational() = runTest{
        coEvery { localDataSource.getEntries() } returns
                list

        val repo = PokemonRepositoryImpl(remoteDataSource, localDataSource)
        assertEquals(repo.getPokedexEntries(),
            list.map{it.toPokedexEntryModel()
        })
    }

    @Test
    fun testGetPokedexEntriesRegion() = runTest{
        coEvery { localDataSource.getEntries() } returns
                list

        val repo = PokemonRepositoryImpl(remoteDataSource, localDataSource)
        assertEquals(repo.getPokedexEntries(DexConfig.KantoDex.region),
            list.map{it.toPokedexEntryModel()}
        )
    }

    @Test
    fun testGetPokedexEntriesRegionFiltered() = runTest{
        coEvery { localDataSource.getEntries() } returns
                list

        val repo = PokemonRepositoryImpl(remoteDataSource, localDataSource)
        assertNotEquals(repo.getPokedexEntries(DexConfig.UnovaDex.region).size, 150)
    }

    //Cant build with empty DTOs, raises an exception
    @Test(expected = Throwable::class)
    fun testGetPokemonDetails_EmptyDTOs() = runTest{
        val id = 1
        val name = "Bulbasaur"
        coEvery { remoteDataSource.getPokemon(id) } returns
                PokemonDTO(
                    id = 1,
                    picture = PictureDTO(ArtworkDTO(OfficialArtworkDTO(""))),
                    abilities = emptyList(),
                    types = emptyList(),
                    moves = emptyList(),
                    species = NameDTO(""),
                    stats = emptyList()
                )
        coEvery { remoteDataSource.getPokemonSpecies(name) } returns
                PokemonSpeciesDTO(
                    eggGroups = emptyList(),
                    captureRate = 0,
                    descriptor = emptyList(),
                    names = emptyList(),
                    pokedexNumbers = emptyList(),
                    pokedexEntries = emptyList(),
                    varieties = emptyList()
                )

        val repo = PokemonRepositoryImpl(remoteDataSource, localDataSource)
        repo.getPokemonDetails(id)
    }


}