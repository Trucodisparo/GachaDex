package com.keepcoding.gachadex.domain.model

import com.keepcoding.gachadex.common.DexConfig
import org.junit.Assert
import org.junit.Test

internal class PokedexEntryModelTest{

    @Test
    fun testConstructor(){
        val pokemon = PokedexEntryModel(id=2, species = "Ivysaur")
        Assert.assertEquals(pokemon::class, PokedexEntryModel::class)
        Assert.assertEquals(pokemon.id, 2)
        Assert.assertEquals(pokemon.species, "Ivysaur")
        Assert.assertEquals(pokemon.dexNumbers.size, DexConfig.regions.size)
    }
}