package com.keepcoding.gachadex.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.keepcoding.gachadex.common.DexConfig

@Entity(tableName = "PokemonTable")
data class PokemonLocal (
    @PrimaryKey @ColumnInfo(name="id") val id: Int,
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="title") val title: String,
    @ColumnInfo(name="pictureURL") val pictureURL: String?,
    @ColumnInfo(name="dexNumber") val dexNumber: Int,
    @ColumnInfo(name="dexNKanto") val dexNKanto: Int?,
    @ColumnInfo(name="dexNJohto") val dexNJohto: Int?,
    @ColumnInfo(name="dexNHoenn") val dexNHoenn: Int?,
    @ColumnInfo(name="dexNSinnoh") val dexNSinnoh: Int?,
    @ColumnInfo(name="dexNUnova") val dexNUnova: Int?,
    @ColumnInfo(name="dexNAlola") val dexNAlola: Int?,
    @ColumnInfo(name="dexNGalar") val dexNGalar: Int?,
    @ColumnInfo(name="dexNHisui") val dexNHisui: Int?,
    @ColumnInfo(name="dexNPaldea") val dexNPaldea: Int?,
    @ColumnInfo(name="primaryType") val primaryType: String,
    @ColumnInfo(name="secondaryType") val secondaryType: String?,
    ){
}