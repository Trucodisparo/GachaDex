package com.keepcoding.gachadex.data.mappers

import com.keepcoding.gachadex.data.local.PokemonLocal
import com.keepcoding.gachadex.domain.model.PokedexEntryModel
import org.junit.Assert.assertEquals
import org.junit.Test

internal class PokemonLocalMappersKtTest{
    val pokemonEM = PokedexEntryModel(
        id = 1,
        picture = "pictureURL",
        species = "name",
        title = "title",
        dexNumbers = mapOf(
        Pair("national", 1),
        Pair("kanto", 1),
        Pair("johto", 1),
        Pair("hoenn", 1),
        Pair("sinnoh", 1),
        Pair("unova", 1),
        Pair("alola", 1),
        Pair("galar", 1),
        Pair("hisui", 1),
        Pair("paldea", 1)
        ),
        primaryType = "primaryType",
        secondaryType = "secondaryType"
    )

    val pokemonL = PokemonLocal(
        id = 1,
        name = "name",
        title = "title",
        pictureURL = "pictureURL",
        dexNumber = 1,
        dexNKanto = 1,
        dexNJohto = 1,
        dexNHoenn = 1,
        dexNSinnoh = 1,
        dexNUnova = 1,
        dexNAlola = 1,
        dexNGalar = 1,
        dexNHisui = 1,
        dexNPaldea = 1,
        primaryType = "primaryType",
        secondaryType = "secondaryType"
    )

    @Test
    fun testEMtoLocal(){
        assertEquals(pokemonEM, pokemonL.toPokedexEntryModel())
    }

    @Test
    fun testLocaltoEM(){
        assertEquals(pokemonEM.toPokemonLocal(), pokemonL)
    }
}