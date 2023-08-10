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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.DefaultDispatcherRule

internal class GetPokedexStatusUseCaseTest{
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
        coEvery { pokedexStatusRepository.getCurrentSettings() } returns
                flow{
                    emit(
                        PokedexStatusModel(
                            DexConfig.KantoDex,
                            DexConfig.KantoDex
                        )
                    )
                }

        val useCase = GetPokedexStatusUseCase(pokedexStatusRepository)
        var result = MutableStateFlow(PokedexStatusModel())
        useCase.invoke().collect{result.value = it}
        assertEquals(result.value, PokedexStatusModel(
            DexConfig.KantoDex,
            DexConfig.KantoDex
        ))
    }
}