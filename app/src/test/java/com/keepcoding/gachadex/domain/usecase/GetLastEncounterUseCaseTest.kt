package com.keepcoding.gachadex.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.keepcoding.gachadex.common.DexConfig
import com.keepcoding.gachadex.data.PokedexStatusRepository
import com.keepcoding.gachadex.domain.model.PokedexStatusModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.DefaultDispatcherRule

internal class GetLastEncounterUseCaseTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val defaultDispatcherRule = DefaultDispatcherRule()

    @MockK(relaxed = true)
    private lateinit var pokedexStatusRepository: PokedexStatusRepository

    @Before
    fun setup(){
        MockKAnnotations.init(this)
    }
    @Test
    fun testInvocation() = runTest{
        coEvery { pokedexStatusRepository.getLastEncounter() } returns
                flow{
                    emit(
                        3000L
                    )
                }

        val useCase = GetLastEncounterUseCase(pokedexStatusRepository)
        var result = MutableStateFlow(0L)
        useCase.invoke().collect{result.value = it}
        Assert.assertEquals(result.value, 3000L)
    }
}