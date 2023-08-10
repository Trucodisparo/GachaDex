package com.keepcoding.gachadex.presentation.mainmenu

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.keepcoding.gachadex.domain.usecase.GetLastEncounterUseCase
import com.keepcoding.gachadex.domain.usecase.SetLastEncounterUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import util.DefaultDispatcherRule

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