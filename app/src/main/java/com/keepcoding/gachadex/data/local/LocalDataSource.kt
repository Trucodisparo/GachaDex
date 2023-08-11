package com.keepcoding.gachadex.data.local

interface LocalDataSource {

    suspend fun getEntries() : List<PokemonLocal>
    suspend fun addEntry(entry: PokemonLocal)
    suspend fun getEntry(id: Int): PokemonLocal?
    suspend fun deleteEntries()

}