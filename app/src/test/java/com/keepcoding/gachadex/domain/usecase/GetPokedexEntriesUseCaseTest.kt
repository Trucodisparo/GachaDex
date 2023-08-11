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

internal class GetPokedexEntriesUseCaseTest{
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
        val list = List(150){ PokedexEntryModel(id=it) }
        coEvery { pokemonRepository.getPokedexEntries() } returns
                list

        val useCase = GetPokedexEntriesUseCase(pokemonRepository)
        var result = MutableStateFlow<List<PokedexEntryModel>>(emptyList())
        useCase.invoke().collect{result.value = it}
        Assert.assertEquals(result.value, list)
    }
}