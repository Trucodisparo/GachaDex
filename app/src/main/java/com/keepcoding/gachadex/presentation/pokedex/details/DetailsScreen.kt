package com.keepcoding.gachadex.presentation.pokedex.details

import android.media.MediaPlayer
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.Glide
import com.keepcoding.gachadex.R
import com.keepcoding.gachadex.databinding.PokemonDetailsBinding
import com.keepcoding.gachadex.ui.theme.TextColor
import org.koin.androidx.compose.koinViewModel


@Composable
fun DetailsScreen(
    id: Int,
    vm: DetailsViewModel = koinViewModel()
) {
    val state = vm.pokemon.collectAsStateWithLifecycle()
    vm.getData(id)
    val mp = MediaPlayer()

    if(state.value.isLoaded && !state.value.isError) {
        state.value.pokemon?.let{pokemon ->
            LaunchedEffect(Unit){
                mp.setDataSource("https://play.pokemonshowdown.com/audio/cries/${pokemon
                    .species
                    .lowercase()
                    .replace("\'", "")
                    .replace("♀", "f")
                    .replace("♂", "m")}.mp3")
                mp.prepare()
                mp.start()
            }
            val hiddenAbilityStr = pokemon.hiddenAbility?.let {
                stringResource(id = R.string.hidden_ability,
                    it
                )
            }
            AndroidViewBinding(PokemonDetailsBinding::inflate) {
                var bgColor = root.context.resources.getIdentifier(pokemon.primaryType, "color", root.context.packageName)
                root.setBackgroundColor(ContextCompat.getColor(root.context, bgColor))
                Glide.with(this.root).load(pokemon.picture).placeholder(R.drawable.pokeball).error(R.drawable.pokeball).into(this.ivPokemon)
                ivPokemon.setOnClickListener {
                    mp.reset()
                    mp.setDataSource("https://play.pokemonshowdown.com/audio/cries/${pokemon
                        .species
                        .lowercase()
                        .replace("\'", "")
                        .replace("♀", "f")
                        .replace("♂", "m")}.mp3")
                    mp.prepare()
                    mp.start()
                }
                tvPokemonName.text = pokemon.species
                tvPokemonNumber.text = String.format("#%04d", pokemon.dexNumber)
                tvPokemonTitle.text = pokemon.title
                tvPokemonType1.text = pokemon.primaryType
                if(pokemon.secondaryType != null) {
                    tvPokemonType2.text = pokemon.secondaryType
                }else {
                    tvPokemonTypeMiddle.visibility = View.GONE
                    tvPokemonType2.visibility = View.GONE
                    tvPokemonType1.gravity = Gravity.CENTER
                }
                tvPokemonDexEntry.text = pokemon.dexEntry
                hpValue.text = pokemon.hp.toString()
                atkValue.text = pokemon.atk.toString()
                defValue.text = pokemon.def.toString()
                spatkValue.text = pokemon.spatk.toString()
                spdefValue.text = pokemon.spdef.toString()
                speedValue.text = pokemon.spe.toString()
                llAbilities.findViewById<TextView>(R.id.Abilities1).text = pokemon.firstAbility
                if(pokemon.secondAbility != null)
                    llAbilities.findViewById<TextView>(R.id.Abilities2).text = pokemon.secondAbility
                else
                    llAbilities.findViewById<TextView>(R.id.Abilities2).visibility = View.GONE
                if(pokemon.hiddenAbility != null)
                    llAbilities.findViewById<TextView>(R.id.AbilitiesH).text = hiddenAbilityStr
                else
                    llAbilities.findViewById<TextView>(R.id.AbilitiesH).visibility = View.GONE
                cwMoveList.apply {
                    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                    setContent {
                        MovesList(list = pokemon.moves)
                    }
                }
            }
        } ?: Text(stringResource(id = R.string.unknown_error))
    }else{
        Column(Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            if (!state.value.isLoaded && !state.value.isError) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onSurface)
            }
            if(state.value.isError){
                Text(state.value.errorMsg ?: stringResource(id = R.string.unknown_error),
                    color = TextColor, textAlign = TextAlign.Center)
            }
        }
    }
}


@Composable
fun MovesList(list: List<String>){
    Column {
        list.forEach {
            Text(it,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = TextColor,
                fontSize = 16.sp
            )
        }
    }
}