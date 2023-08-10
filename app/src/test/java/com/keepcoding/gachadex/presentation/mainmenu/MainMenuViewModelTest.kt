package com.keepcoding.gachadex.presentation.mainmenu

import android.os.CountDownTimer
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.keepcoding.gachadex.domain.usecase.GetCurrentSettingsUseCase
import com.keepcoding.gachadex.domain.usecase.GetLastEncounterUseCase
import com.keepcoding.gachadex.domain.usecase.GetPokedexEntriesUseCase
import com.keepcoding.gachadex.domain.usecase.ResetPokedexUseCase
import com.keepcoding.gachadex.domain.usecase.SetCurrentSettingsUseCase
import com.keepcoding.gachadex.domain.usecase.SetLastEncounterUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.atLeast
import util.DefaultDispatcherRule
import java.util.concurrent.TimeUnit

internal class MainMenuViewModelTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val defaultDispatcherRule = DefaultDispatcherRule()

    @MockK(relaxed = true)
    private lateinit var getLastEncounterUseCase: GetLastEncounterUseCase
    @MockK(relaxed = true)
    private lateinit var setLastEncounterUseCase: SetLastEncounterUseCase

    @Before
    fun setup(){
        MockKAnnotations.init(this)
    }

    /*
    @Test
    fun testSetLastEncounter() = runTest{
        coEvery { setLastEncounterUseCase.invoke() } returns Unit
        coEvery { getLastEncounterUseCase.invoke() } returns
            flow{
                emit(
                    System.currentTimeMillis()
                )
            }

        val vm = MainMenuViewModel(getLastEncounterUseCase, setLastEncounterUseCase)
        vm.countDown.test{

        }
    }*/
}