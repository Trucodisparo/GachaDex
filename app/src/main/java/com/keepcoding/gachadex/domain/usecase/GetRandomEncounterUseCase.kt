package com.keepcoding.gachadex.domain.usecase;

import com.keepcoding.gachadex.data.PokemonRepository;
import com.keepcoding.gachadex.domain.model.PokedexEntryModel;
import kotlinx.coroutines.flow.flow

import java.util.List;

class GetRandomEncounterUseCase(
    private val repository: PokemonRepository
){
    suspend fun invoke(region: String = "national") = flow<Pair<PokedexEntryModel, Boolean>>{emit(repository.getRandomPokemon(region))}
}