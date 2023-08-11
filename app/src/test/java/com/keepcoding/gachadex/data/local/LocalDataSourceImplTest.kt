package com.keepcoding.gachadex.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.keepcoding.gachadex.data.local.model.PokemonDAO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.DefaultDispatcherRule

internal class LocalDataSourceImplTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val defaultDispatcherRule = DefaultDispatcherRule()

    @MockK(relaxed = true)
    private lateinit var dao: PokemonDAO

    @Before
    fun setup(){
        MockKAnnotations.init(this)
    }

    private val list = List(150){ PokemonLocal(id = it+1,
        name = "Pokemon$it",
        title = "Pokemon n$it",
        dexNumber = it+1,
        dexNKanto = it+1,
    )}

    @Test
    fun testGetEntries() = runTest{
        coEvery{dao.getAll()} returns list

        val src = LocalDataSourceImpl(dao)
        assertEquals(src.getEntries(), list)
    }

    @Test
    fun testGetEntry() = runTest{
        val id = 1
        coEvery{dao.getPokemonbyId(id)} returns PokemonLocal(id=1)

        val src = LocalDataSourceImpl(dao)
        assertEquals(src.getEntry(id), PokemonLocal(id=1))
    }

    @Test
    fun testAddEntry() = runTest{
        val pokemon = PokemonLocal()
        coEvery { dao.insert(pokemon) } returns Unit

        val src = LocalDataSourceImpl(dao)
        src.addEntry(pokemon) //noExcept
    }

    @Test
    fun testDeleteEntries() = runTest{
        coEvery { dao.deleteAll() } returns Unit

        val src = LocalDataSourceImpl(dao)
        src.deleteEntries() //noExcept
    }
}