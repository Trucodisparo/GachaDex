package com.keepcoding.gachadex.presentation.pokedex

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.keepcoding.gachadex.common.DexConfig
import com.keepcoding.gachadex.domain.model.PokedexEntryModel
import com.keepcoding.gachadex.domain.model.PokedexStatusModel
import com.keepcoding.gachadex.domain.usecase.GetCurrentSettingsUseCase
import com.keepcoding.gachadex.domain.usecase.GetPokedexEntriesUseCase
import com.keepcoding.gachadex.domain.usecase.ResetPokedexUseCase
import com.keepcoding.gachadex.domain.usecase.SetCurrentSettingsUseCase
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

internal class PokedexViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val defaultDispatcherRule = DefaultDispatcherRule()

    @MockK(relaxed = true)
    private lateinit var getPokedexEntriesUseCase: GetPokedexEntriesUseCase
    @MockK(relaxed = true)
    private lateinit var getCurrentSettingsUseCase: GetCurrentSettingsUseCase
    @MockK(relaxed = true)
    private lateinit var setCurrentSettingsUseCase: SetCurrentSettingsUseCase
    @MockK(relaxed = true)
    private lateinit var resetPokedexUseCase: ResetPokedexUseCase


    @Before
    fun setup(){
        MockKAnnotations.init(this)
    }

    @Test
    fun testGetData() = runTest{
        val settings = PokedexStatusModel(
                current_dex = DexConfig.KantoDex,
                last_unlocked = DexConfig.KantoDex
            )

        val list = List(5){index ->
            PokedexEntryModel(id = index)
        }
        val region = DexConfig.KantoDex.region
        coEvery{ getCurrentSettingsUseCase.invoke() } returns flow{emit(settings)}
        coEvery { getPokedexEntriesUseCase.invoke(region) } returns
                flow{
                    emit(
                        list
                    )
                }
        coEvery { setCurrentSettingsUseCase.invoke(settings) } returns Unit

        val vm = PokedexViewModel(
            getPokedexEntriesUseCase,
            getCurrentSettingsUseCase,
            setCurrentSettingsUseCase,
            resetPokedexUseCase
        )

        vm.pokedex.test{
            vm.getData(region)
            assertEquals(PokedexState(
                isLoaded = false,
                list = emptyList(),
            ), awaitItem())
            assertEquals(PokedexState(
                isLoaded = true,
                list = list,
                currentRegion = region
            ), awaitItem())
        }
    }

    @Test
    fun testGetData_Error() = runTest{
        val settings = PokedexStatusModel(
            current_dex = DexConfig.KantoDex,
            last_unlocked = DexConfig.KantoDex
        )
        val region = DexConfig.KantoDex.region
        val exception = Throwable("An exception was thrown")
        coEvery{ getCurrentSettingsUseCase.invoke() } returns flow{emit(settings)}
        coEvery { getPokedexEntriesUseCase.invoke(region) } throws exception
        coEvery { setCurrentSettingsUseCase.invoke(settings) } returns Unit

        val vm = PokedexViewModel(
            getPokedexEntriesUseCase,
            getCurrentSettingsUseCase,
            setCurrentSettingsUseCase,
            resetPokedexUseCase
        )

        vm.pokedex.test{
            vm.getData(region)
            assertEquals(PokedexState(
                isLoaded = false,
                list = emptyList(),
            ), awaitItem())
            assertEquals(PokedexState(
                isError = true,
                errorMsg = exception.toString(),
            ), awaitItem())
        }
    }
}