package com.keepcoding.gachadex.presentation.encounter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.keepcoding.gachadex.common.DexConfig
import com.keepcoding.gachadex.domain.model.PokedexEntryModel
import com.keepcoding.gachadex.domain.model.PokedexStatusModel
import com.keepcoding.gachadex.domain.usecase.GetCurrentSettingsUseCase
import com.keepcoding.gachadex.domain.usecase.GetRandomEncounterUseCase
import com.keepcoding.gachadex.domain.usecase.RegisterPokemonUseCase
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

internal class EncounterViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val defaultDispatcherRule = DefaultDispatcherRule()

    @MockK(relaxed = true)
    private lateinit var getRandomEncounterUseCase: GetRandomEncounterUseCase
    @MockK(relaxed = true)
    private lateinit var registerPokemonUseCase: RegisterPokemonUseCase
    @MockK(relaxed = true)
    private lateinit var getCurrentSettingsUseCase: GetCurrentSettingsUseCase

    @Before
    fun setup(){
        MockKAnnotations.init(this)
    }
    
    @Test
    fun getRandomEncounter() = runTest{
        val region = DexConfig.KantoDex.region
        val pokemon = PokedexEntryModel(id=1)
        coEvery{ getRandomEncounterUseCase.invoke(region) } returns
                flow{
                    emit(
                        Pair(pokemon, false)
                    )
                }
        coEvery{ getCurrentSettingsUseCase.invoke() } returns
                flow{
                    emit(
                        PokedexStatusModel(
                            current_dex = DexConfig.KantoDex,
                            last_unlocked = DexConfig.KantoDex
                        )
                    )
                }

        val vm = EncounterViewModel(getRandomEncounterUseCase, registerPokemonUseCase, getCurrentSettingsUseCase)
        vm.encounter.test{
            vm.getRandomEncounter()
            assertEquals(EncounterState(), awaitItem())
            assertEquals(EncounterState(isLoaded = true, pokemon, isRegistered = false), awaitItem())
        }
    }

    fun getRandomEncounter_Error() = runTest{
        val region = DexConfig.KantoDex.region
        val pokemon = PokedexEntryModel(id=1)
        coEvery{ getRandomEncounterUseCase.invoke(region) } returns
                flow{
                    emit(
                        Pair(pokemon, false)
                    )
                }
        coEvery{ getCurrentSettingsUseCase.invoke() } returns
                flow{
                    emit(
                        PokedexStatusModel(
                            current_dex = DexConfig.KantoDex,
                            last_unlocked = DexConfig.KantoDex
                        )
                    )
                }

        val vm = EncounterViewModel(getRandomEncounterUseCase, registerPokemonUseCase, getCurrentSettingsUseCase)
        vm.encounter.test{
            vm.getRandomEncounter()
            assertEquals(EncounterState(), awaitItem())
            assertEquals(EncounterState(isLoaded = true, pokemon, isRegistered = false), awaitItem())
        }
    }
}