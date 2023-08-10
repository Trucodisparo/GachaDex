package com.keepcoding.gachadex.domain.model

import com.keepcoding.gachadex.common.DexConfig
import org.junit.Assert.assertEquals
import org.junit.Test


internal class PokedexStatusModelTest{
    @Test
    fun testConstructor_Empty(){
        val settings = PokedexStatusModel()
        assertEquals(settings.current_dex, DexConfig.NatDex)
        assertEquals(settings.last_unlocked, DexConfig.NatDex)
    }

    @Test
    fun testConstructor(){
        val settings = PokedexStatusModel(DexConfig.KantoDex, DexConfig.SinnohDex)
        assertEquals(settings.current_dex, DexConfig.KantoDex)
        assertEquals(settings.last_unlocked, DexConfig.SinnohDex)
    }
}