package com.keepcoding.gachadex.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.keepcoding.gachadex.data.PokemonRepository
import com.keepcoding.gachadex.domain.model.PokedexEntryModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.DefaultDispatcherRule

internal class GetRandomEncounterUseCaseTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val defaultDispatcherRule = DefaultDispatcherRule()

    @MockK(relaxed = true)
    private lateinit var pokemonRepository: PokemonRepository

    @Before
    fun setup(){
        MockKAnnotations.init(this)
    }
    @Test
    fun testInvocation() = runTest{
        val id = 1
        val pokemon = PokedexEntryModel(id=id)
        coEvery { pokemonRepository.getRandomPokemon() } returns
                Pair(PokedexEntryModel(id=id), false)

        val useCase = GetRandomEncounterUseCase(pokemonRepository)
        var result = MutableStateFlow(Pair(PokedexEntryModel(), false))
        useCase.invoke().collect{result.value = it}
        Assert.assertEquals(result.value, Pair(pokemon, false))
    }
}