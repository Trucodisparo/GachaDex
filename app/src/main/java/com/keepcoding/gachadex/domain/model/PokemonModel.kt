package com.keepcoding.gachadex.domain.model

data class PokemonModel(
    val id: Int =  0,
    val picture: String? = "https://upload.wikimedia.org/wikipedia/commons/6/62/MissingNo.png",
    val species: String = "MISSINGNO",
    val title: String = "",
    val dexNumber: Int = 0,
    val primaryType: String = "???",
    val secondaryType: String? = null,
    val eggGroup_1: String = "",
    val eggGroup_2: String? = null,
    val captureRate: Int = 0,
    val dexEntry: String = "",
    val firstAbility: String = "",
    val secondAbility: String? = null,
    val hiddenAbility: String? = null,
    val moves: List<String> = emptyList(),
    val hp: Int = 0,
    val atk: Int = 0,
    val def: Int = 0,
    val spatk: Int = 0,
    val spdef: Int = 0,
    val spe: Int = 0
)
