package com.keepcoding.gachadex.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.keepcoding.gachadex.data.PokedexStatusRepository
import com.keepcoding.gachadex.data.PokemonRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.DefaultDispatcherRule


internal class ResetPokedexUseCaseTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val defaultDispatcherRule = DefaultDispatcherRule()

    @MockK(relaxed = true)
    private lateinit var pokemonRepository: PokemonRepository
    @MockK(relaxed = true)
    private lateinit var pokedexStatusRepository: PokedexStatusRepository

    @Before
    fun setup(){
        MockKAnnotations.init(this)
    }

    @Test
    fun testInvocation() = runTest {
        coEvery { pokemonRepository.clearPokedex() } returns Unit
        coEvery { pokedexStatusRepository.clearSettings() } returns Unit

        val useCase = ResetPokedexUseCase(pokemonRepository, pokedexStatusRepository)
        useCase.invoke() //Noexcept
    }
}