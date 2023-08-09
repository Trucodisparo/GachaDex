package com.keepcoding.gachadex.data.mappers

import androidx.room.ColumnInfo
import com.keepcoding.gachadex.data.local.PokemonLocal
import com.keepcoding.gachadex.domain.model.PokedexEntryModel
import com.keepcoding.gachadex.domain.model.PokemonModel

fun PokemonLocal.toPokedexEntryModel() = PokedexEntryModel(
    id = id,
    picture = pictureURL,
    species = name,
    title = title,
    dexNumbers = mapOf(
        Pair("national", dexNumber),
        Pair("kanto", dexNKanto),
        Pair("johto", dexNJohto),
        Pair("hoenn", dexNHoenn),
        Pair("sinnoh", dexNSinnoh),
        Pair("unova", dexNUnova),
        Pair("alola", dexNAlola),
        Pair("galar", dexNGalar),
        Pair("hisui", dexNHisui),
        Pair("paldea", dexNPaldea)
        ),
    primaryType = primaryType,
    secondaryType = secondaryType
)

fun PokedexEntryModel.toPokemonLocal() = PokemonLocal(
    id = id,
    name = species,
    title = title,
    pictureURL = picture,
    dexNumber = dexNumbers["national"] ?: 0,
    dexNKanto = dexNumbers["kanto"],
    dexNJohto = dexNumbers["johto"],
    dexNHoenn = dexNumbers["hoenn"],
    dexNSinnoh = dexNumbers["sinnoh"],
    dexNUnova = dexNumbers["unova"],
    dexNAlola = dexNumbers["alola"],
    dexNGalar = dexNumbers["galar"],
    dexNHisui = dexNumbers["hisui"],
    dexNPaldea = dexNumbers["paldea"],
    primaryType = primaryType,
    secondaryType = secondaryType
)