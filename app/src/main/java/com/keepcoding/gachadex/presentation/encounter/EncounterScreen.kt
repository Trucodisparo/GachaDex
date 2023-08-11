package com.keepcoding.gachadex.presentation.encounter

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.keepcoding.gachadex.R
import com.keepcoding.gachadex.domain.model.PokedexEntryModel
import com.keepcoding.gachadex.ui.theme.PokedexCyan
import com.keepcoding.gachadex.ui.theme.TextColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun EncounterScreen(
    vm: EncounterViewModel = koinViewModel(),
    onBackClick: () -> Unit
){
    val context = LocalContext.current
    val state = vm.encounter.collectAsStateWithLifecycle()
    var buttonActive = remember {mutableStateOf(false)}
    var headerText = remember {mutableStateOf("A wild Pokémon has appeared!")}
    LaunchedEffect(vm) { vm.getRandomEncounter() }

    if(state.value.isLoaded && !state.value.isError) {
        state.value.pokemon?.let{pokemon ->
            LaunchedEffect(Unit){
                try {
                    val mp = MediaPlayer()
                    mp.setDataSource(
                        "https://play.pokemonshowdown.com/audio/cries/${
                            pokemon
                                .species
                                .lowercase()
                                .replace("\'", "")
                                .replace("♀", "f")
                                .replace("♂", "m")
                        }.mp3"
                    )
                    mp.prepare()
                    mp.setOnCompletionListener {
                        buttonActive.value = true
                        it.release()
                    }
                    mp.start()
                }catch(_: Throwable){}
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(headerText.value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextColor)
                }
                Row(modifier = Modifier.weight(2f)) {
                    EncounterData(pokemon, state.value.isRegistered)
                }
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.Top
                ) {
                    if(buttonActive.value) {
                        Button(onClick = {
                            if (state.value.isRegistered) {
                                vm.clearEncounter()
                                onBackClick()
                            } else {
                                vm.registerRandomEncounter()
                                headerText.value = "${pokemon.species} was registered!"
                                buttonActive.value = false
                                val mp = MediaPlayer.create(context, R.raw.pokemon_caught)
                                mp.setOnCompletionListener {
                                    buttonActive.value = true
                                    it.release()
                                }
                                mp.start()
                                Log.d("MUSICPLAYER", Uri.parse("android.resource://com.keepcoding.gachadex/" + R.raw.pokemon_caught).toString())
                            }
                        }, enabled = buttonActive.value,
                        colors = ButtonDefaults.buttonColors(PokedexCyan)) {
                            val buttonText =
                                if (!state.value.isRegistered) "Register" else "Go away"
                            Text(buttonText, color = TextColor)
                        }
                    }
                }
            }
        }
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

@Preview
@Composable
fun EncounterScreenPreview(){
    EncounterScreen(koinViewModel(),{})
}


@Composable
fun EncounterData(pokemon: PokedexEntryModel, registered: Boolean){
    val context = LocalContext.current
    val colorId = remember(pokemon.primaryType){
        if(pokemon.primaryType != "???") {
            context.resources.getIdentifier(
                pokemon.primaryType.replaceFirstChar { it.uppercase() },
                "color",
                context.packageName
            )
        }
        else R.color.NoType
    }
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 8.dp)
        .aspectRatio(1f),
        colors = CardDefaults.cardColors(containerColor = colorResource(colorId))) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.picture)
                    .build(),
                stringResource(id = R.string.pokemon_image, pokemon.species),
                modifier = Modifier
                    .fillMaxSize(0.5f),
                placeholder = painterResource(id = R.drawable.pokeball),
                error = painterResource(id = R.drawable.pokeball)
            )
            Text(
                String.format("#%04d", pokemon.dexNumbers["national"]),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor
            )
            Row {
                Text(pokemon.species, fontSize = 20.sp, fontWeight = FontWeight.Bold,color = TextColor)
                if(registered) {
                    Spacer(modifier = Modifier.size(4.dp))
                    Image(
                        painterResource(id = R.drawable.pokeball),
                        stringResource(id = R.string.pokemon_caught_icon), modifier = Modifier
                            .size(14.dp)
                            .align(Alignment.CenterVertically),
                        colorFilter = ColorFilter.tint(TextColor),
                        )
                    }
                }
            Text(pokemon.title, color = TextColor)
            val typing = if (pokemon.secondaryType != null)
                "${pokemon.primaryType}/${pokemon.secondaryType}"
            else
                pokemon.primaryType
            Text(typing, color = TextColor)
        }
    }
}