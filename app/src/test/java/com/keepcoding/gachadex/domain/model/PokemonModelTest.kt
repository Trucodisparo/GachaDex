package com.keepcoding.gachadex.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

internal class PokemonModelTest{

    @Test
    fun testConstructor(){
        val pokemon = PokemonModel(id=2, species = "Ivysaur")
        assertEquals(pokemon::class, PokemonModel::class)
        assertEquals(pokemon.id, 2)
        assertEquals(pokemon.species, "Ivysaur")
        assertEquals(pokemon.hiddenAbility, PokemonModel().hiddenAbility)
    }
}