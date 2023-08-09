package com.keepcoding.gachadex.data.local.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.keepcoding.gachadex.data.local.PokemonLocal

@Dao
interface PokemonDAO {

    @Query("SELECT * FROM PokemonTable ORDER BY dexNumber, id")
    suspend fun getAll(): List<PokemonLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: PokemonLocal)

    @Query("SELECT * FROM PokemonTable WHERE id=:id")
    suspend fun getPokemonbyId(id: Int): PokemonLocal?

    @Query("DELETE FROM PokemonTable")
    suspend fun deleteAll()
}