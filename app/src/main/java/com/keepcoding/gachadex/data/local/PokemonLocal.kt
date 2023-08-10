package com.keepcoding.gachadex.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.keepcoding.gachadex.common.DexConfig

@Entity(tableName = "PokemonTable")
data class PokemonLocal (
    @PrimaryKey @ColumnInfo(name="id") val id: Int = 0,
    @ColumnInfo(name="name") val name: String = "Missingno",
    @ColumnInfo(name="title") val title: String = "The Glitch Pokémon",
    @ColumnInfo(name="pictureURL") val pictureURL: String? = "https://upload.wikimedia.org/wikipedia/commons/6/62/MissingNo.png",
    @ColumnInfo(name="dexNumber") val dexNumber: Int = 0,
    @ColumnInfo(name="dexNKanto") val dexNKanto: Int?= null,
    @ColumnInfo(name="dexNJohto") val dexNJohto: Int?= null,
    @ColumnInfo(name="dexNHoenn") val dexNHoenn: Int?= null,
    @ColumnInfo(name="dexNSinnoh") val dexNSinnoh: Int?= null,
    @ColumnInfo(name="dexNUnova") val dexNUnova: Int?= null,
    @ColumnInfo(name="dexNAlola") val dexNAlola: Int?= null,
    @ColumnInfo(name="dexNGalar") val dexNGalar: Int?= null,
    @ColumnInfo(name="dexNHisui") val dexNHisui: Int?= null,
    @ColumnInfo(name="dexNPaldea") val dexNPaldea: Int?= null,
    @ColumnInfo(name="primaryType") val primaryType: String= "???",
    @ColumnInfo(name="secondaryType") val secondaryType: String?= null,
    ){
}