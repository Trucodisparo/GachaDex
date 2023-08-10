package com.keepcoding.gachadex.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.keepcoding.gachadex.data.PokedexStatusRepository
import com.keepcoding.gachadex.domain.model.PokedexStatusModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.DefaultDispatcherRule

internal class SetPokedexStatusUseCaseTest{
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
        val pokedexStatus = PokedexStatusModel()
        coEvery { pokedexStatusRepository.setSettings(pokedexStatus) } returns Unit

        val useCase = SetPokedexStatusUseCase(pokedexStatusRepository)
        useCase.invoke(pokedexStatus)
    }
}