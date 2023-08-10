package com.keepcoding.gachadex.presentation.pokedex.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.keepcoding.gachadex.domain.model.PokemonModel
import com.keepcoding.gachadex.domain.usecase.GetPokemonDetailsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.DefaultDispatcherRule


internal class DetailsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val defaultDispatcherRule = DefaultDispatcherRule()

    @MockK(relaxed = true)
    private lateinit var getPokemonDetailsUseCase: GetPokemonDetailsUseCase

    @Before
    fun setup(){
        MockKAnnotations.init(this)
    }

    @Test
    fun getPokemonDetails() = runTest{
        val vm = DetailsViewModel(getPokemonDetailsUseCase)
        val id = 1
        val pokemon = PokemonModel(id = 1, species = "Bulbasaur")
        val state = DetailsState(
            isLoaded = true,
            pokemon = pokemon,
        )
        coEvery{ getPokemonDetailsUseCase.invoke(id) } returns
                flow { emit (pokemon)}

        vm.pokemon.test{
            vm.getData(id)
            assertEquals(DetailsState(), awaitItem())
            assertEquals(state, awaitItem())
        }
    }

    @Test
    fun getPokemonDetails_Error() = runTest{
        val vm = DetailsViewModel(getPokemonDetailsUseCase)
        val id = 1
        val exception = Throwable("An exception happened")
        val state = DetailsState(
            isError = true,
            errorMsg = exception.toString()
        )
        coEvery{ getPokemonDetailsUseCase.invoke(id) } throws
                exception

        vm.pokemon.test{
            vm.getData(id)
            assertEquals(DetailsState(), awaitItem())
            assertEquals(state, awaitItem())
        }
    }
}