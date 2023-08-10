package com.keepcoding.gachadex.domain.model

data class PokedexEntryModel (
    val id: Int =  0,
    val picture: String? = "https://upload.wikimedia.org/wikipedia/commons/6/62/MissingNo.png",
    val species: String = "MISSINGNO",
    val title: String = "The Glitch Pokémon",
    val dexNumbers: Map<String, Int?> = mapOf(
        Pair("national", 0),
        Pair("kanto", null),
        Pair("johto", null),
        Pair("hoenn", null),
        Pair("sinnoh", null),
        Pair("unova", null),
        Pair("alola", null),
        Pair("galar", null),
        Pair("hisui", null),
        Pair("paldea", null)
        ),
    val primaryType: String = "???",
    val secondaryType: String? = null,
)