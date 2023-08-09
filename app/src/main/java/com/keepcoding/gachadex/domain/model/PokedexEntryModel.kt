package com.keepcoding.gachadex.domain.model

data class PokedexEntryModel (
    val id: Int =  0,
    val picture: String? = "https://upload.wikimedia.org/wikipedia/commons/6/62/MissingNo.png",
    val species: String = "MISSINGNO",
    val title: String = "The Glitch Pokémon",
    val dexNumbers: Map<String, Int?> = mapOf(
        Pair("national", 0),
        Pair("kanto", 0),
        Pair("johto", 0),
        Pair("hoenn", 0),
        Pair("sinnoh", 0),
        Pair("unova", 0),
        Pair("alola", 0),
        Pair("galar", 0),
        Pair("hisui", 0),
        Pair("paldea", 0)
        ),
    val primaryType: String = "???",
    val secondaryType: String? = null,
)