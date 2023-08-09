package com.keepcoding.gachadex.data.local

import com.keepcoding.gachadex.data.local.model.PokemonDAO

class LocalDataSourceImpl(
    private val pokemonDAO: PokemonDAO
) : LocalDataSource {

    override suspend fun getEntries() = pokemonDAO.getAll()

    override suspend fun getEntry(id: Int): PokemonLocal? = pokemonDAO.getPokemonbyId(id)

    override suspend fun addEntry(entry: PokemonLocal) = pokemonDAO.insert(entry)

    override suspend fun deleteEntries() = pokemonDAO.deleteAll()
}