package com.keepcoding.gachadex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.keepcoding.gachadex.data.local.model.PokemonDAO

@Database(entities = [PokemonLocal::class], version = 1, exportSchema = false)
abstract class PokedexDatabase: RoomDatabase() {
    abstract fun pokemonDAO(): PokemonDAO
}