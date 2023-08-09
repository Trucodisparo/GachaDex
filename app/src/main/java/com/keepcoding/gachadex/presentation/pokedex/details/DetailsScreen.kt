package com.keepcoding.gachadex.presentation.pokedex.details

import android.media.MediaPlayer
import android.opengl.Visibility
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.Glide
import com.keepcoding.gachadex.R
import com.keepcoding.gachadex.databinding.PokemonDetailsBinding
import com.keepcoding.gachadex.presentation.pokedex.PokedexViewModel
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
                mp.setDataSource("https://play.pokemonshowdown.com/audio/cries/${pokemon.species.lowercase()}.mp3")
                mp.prepare()
                mp.start()
            }
            AndroidViewBinding(PokemonDetailsBinding::inflate) {
                var bgColor = root.context.resources.getIdentifier(pokemon.primaryType, "color", root.context.packageName)
                root.setBackgroundColor(ContextCompat.getColor(root.context, bgColor))
                Glide.with(this.root).load(pokemon.picture).placeholder(R.drawable.pokeball).error(R.drawable.pokeball).into(this.ivPokemon)
                ivPokemon.setOnClickListener {
                    mp.reset()
                    mp.setDataSource("https://play.pokemonshowdown.com/audio/cries/${pokemon.species.lowercase()}.mp3")
                    mp.prepare()
                    mp.start()
                }
                tvPokemonName.text = pokemon.species
                tvPokemonNumber.text = String.format("#%03d", pokemon.dexNumber)
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
                    llAbilities.findViewById<TextView>(R.id.AbilitiesH).text = pokemon.hiddenAbility + " (Hidden)"
                else
                    llAbilities.findViewById<TextView>(R.id.AbilitiesH).visibility = View.GONE
                cwMoveList.apply {
                    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                    setContent {
                        MovesList(list = pokemon.moves)
                    }
                }
            }
        } ?: Text("Unknown error")
    }
}


@Composable
fun MovesList(list: List<String>){
    Column() {
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