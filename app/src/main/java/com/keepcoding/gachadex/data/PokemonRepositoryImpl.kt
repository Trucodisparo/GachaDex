package com.keepcoding.gachadex.data

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.keepcoding.gachadex.common.DexConfig
import com.keepcoding.gachadex.data.local.LocalDataSource
import com.keepcoding.gachadex.data.remote.RemoteDataSource
import com.keepcoding.gachadex.data.dto.PokemonDTO
import com.keepcoding.gachadex.data.dto.PokemonSpeciesDTO
import com.keepcoding.gachadex.data.local.PokemonLocal
import com.keepcoding.gachadex.data.mappers.toPokedexEntryModel
import com.keepcoding.gachadex.data.mappers.toPokemonLocal
import com.keepcoding.gachadex.domain.model.PokedexEntryModel
import com.keepcoding.gachadex.domain.model.PokemonModel
import kotlin.random.Random

class PokemonRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): PokemonRepository {
    override suspend fun getPokedexEntries(region: String): List<PokedexEntryModel> = localDataSource.getEntries().filter{
        val lastValidEntry = DexConfig.getDexByRegion(region).last
        when(region){
            "kanto" -> it.dexNKanto?.let{dexN -> dexN <= lastValidEntry} ?: false
            "johto" -> it.dexNJohto?.let{dexN -> dexN <= lastValidEntry} ?: false
            "hoenn" -> it.dexNHoenn?.let{dexN -> dexN <= lastValidEntry} ?: false
            "sinnoh" -> it.dexNSinnoh?.let{dexN -> dexN <= lastValidEntry} ?: false
            "unova" -> it.dexNUnova?.let{dexN -> dexN <= lastValidEntry} ?: false
            "alola" -> it.dexNAlola?.let{dexN -> dexN <= lastValidEntry} ?: false
            "galar" -> it.dexNGalar?.let{dexN -> dexN <= lastValidEntry} ?: false
            "hisui" -> it.dexNHisui?.let{dexN -> dexN <= lastValidEntry} ?: false
            "paldea" -> it.dexNPaldea?.let{dexN -> dexN <= lastValidEntry} ?: false
            else -> true
        }
    }.map{it.toPokedexEntryModel()}

    override suspend fun getPokemonDetails(id: Int): PokemonModel {
        val pokemon = remoteDataSource.getPokemon(id)
        val pokemonSpecies = remoteDataSource.getPokemonSpecies(pokemon.species.name)

        return buildPokemonModel(pokemon, pokemonSpecies)
    }

    override suspend fun getRandomPokemon(region: String): Pair<PokedexEntryModel, Boolean>{
        val dexConfig = DexConfig.getDexByRegion(region)
        val pokemonSpecies = remoteDataSource.getPokemonSpecies(Random.nextInt(1,dexConfig.last+1))
        val variant = getValidVariant(pokemonSpecies)
        val pokemon = remoteDataSource.getPokemon(variant)
        val isRegistered = (localDataSource.getEntry(pokemon.id) != null)

        return Pair(buildPokedexEntryModel(pokemon, pokemonSpecies), isRegistered)
    }

    override suspend fun registerPokemon(pokemon: PokedexEntryModel){
        println(pokemon)
        println(pokemon.toPokemonLocal())
        localDataSource.addEntry(pokemon.toPokemonLocal())
    }

    override suspend fun clearPokedex() = localDataSource.deleteEntries()


    private fun buildPokemonModel(pokemon: PokemonDTO, pokemonSpecies: PokemonSpeciesDTO): PokemonModel{
        return PokemonModel(
            id = pokemon.id,
            picture = pokemon.picture.artworks.officialArtwork.url,
            species = pokemonSpecies.names.find { it.language.name.equals("en")}?.name ?: throw NoSuchElementException("Couldn't find the Pokémon's name"),
            title = pokemonSpecies.descriptor.find{it.language.name == "en"}?.genus ?: throw NoSuchElementException("Couldn't find the Pokémon's genus"),
            dexNumber = pokemonSpecies.pokedexNumbers.find{it.pokedex.name == "national"}?.number ?: throw NoSuchElementException("Couldn't find the Pokémon's dex number"),
            primaryType = pokemon.types.first().type.name.replaceFirstChar { it.uppercase() },
            secondaryType = if(pokemon.types.size == 2) pokemon.types[1].type.name.replaceFirstChar{it.uppercase()} else null,
            eggGroup_1 = pokemonSpecies.eggGroups.first().name.replaceFirstChar { it.uppercase() },
            eggGroup_2 = if(pokemonSpecies.eggGroups.size == 2) pokemonSpecies.eggGroups[1].name.replaceFirstChar { it.uppercase() } else null,
            captureRate = pokemonSpecies.captureRate,
            dexEntry = pokemonSpecies.pokedexEntries.find{it.language.name == "en"}?.text?.replace("\n", " ") ?: throw NoSuchElementException("Couldn't find the Pokémon's dex entry"),
            firstAbility = pokemon.abilities.first().ability.name.replaceFirstChar { it.uppercase() },
            secondAbility = pokemon.abilities.find{ it.slot == 2 && !it.isHidden }?.ability?.name?.replaceFirstChar { it.uppercase() },
            hiddenAbility = pokemon.abilities.find{ it.isHidden }?.ability?.name?.replaceFirstChar { it.uppercase() },
            moves = pokemon.moves.map{it.move.name.replaceFirstChar { c-> c.uppercase() }},
            hp = pokemon.stats.find{it.name.name == "hp"}?.value ?: throw NoSuchElementException("Couldn't find the Pokémon's hp stat"),
            atk = pokemon.stats.find{it.name.name == "attack"}?.value ?: throw NoSuchElementException("Couldn't find the Pokémon's attack stat"),
            def = pokemon.stats.find{it.name.name == "defense"}?.value ?: throw NoSuchElementException("Couldn't find the Pokémon's defense stat"),
            spatk = pokemon.stats.find{it.name.name == "special-attack"}?.value ?: throw NoSuchElementException("Couldn't find the Pokémon's special attack stat"),
            spdef = pokemon.stats.find{it.name.name == "special-defense"}?.value ?: throw NoSuchElementException("Couldn't find the Pokémon's special defense stat"),
            spe = pokemon.stats.find{it.name.name == "speed"}?.value ?: throw NoSuchElementException("Couldn't find the Pokémon's speed stat")
        )
    }

    private fun buildPokedexEntryModel(pokemon: PokemonDTO, pokemonSpecies: PokemonSpeciesDTO): PokedexEntryModel{
        return PokedexEntryModel(
            id = pokemon.id,
            picture = pokemon.picture.artworks.officialArtwork.url,
            species = pokemonSpecies.names.find { it.language.name.equals("en")}?.name ?: throw NoSuchElementException("Couldn't find the Pokémon's name"),
            title = pokemonSpecies.descriptor.find{it.language.name == "en"}?.genus ?: throw NoSuchElementException("Couldn't find the Pokémon's genus"),
            dexNumbers = DexConfig.regions.associateWith { region ->
                pokemonSpecies.pokedexNumbers.find {
                    it.pokedex.name == DexConfig.getDexByRegion(region).name
                }?.number
            },
            primaryType = pokemon.types.first().type.name.replaceFirstChar { it.uppercase() },
            secondaryType = if(pokemon.types.size == 2) pokemon.types[1].type.name.replaceFirstChar { it.uppercase() } else null
        )
    }

    private fun getValidVariant(pokemon: PokemonSpeciesDTO): String{
        val variantIndexes = (0 until pokemon.varieties.size).toMutableList()
        variantIndexes.shuffle()
        while(variantIndexes.size > 0){
            val variant = pokemon.varieties[variantIndexes.first()].pokemon.name
            Log.d("RANDOM_ENCOUNTER_GEN", "variant: $variant")
            if(!(variant.endsWith("-mega") || variant.endsWith("-mega-x") || variant.endsWith("-mega-y")|| variant.endsWith("-gmax") || variant.endsWith("-totem")))
                return variant
            else
                variantIndexes.remove(variantIndexes.first())
        }
        throw NotFoundException("Couldn't find a valid variant")
    }
}