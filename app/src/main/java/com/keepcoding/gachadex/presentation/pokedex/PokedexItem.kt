package com.keepcoding.gachadex.presentation.pokedex

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.keepcoding.gachadex.R
import com.keepcoding.gachadex.domain.model.PokedexEntryModel
import com.keepcoding.gachadex.ui.theme.TextColor

@Composable
fun PokedexItem(
    pokemon: PokedexEntryModel,
    region: String,
    onClick: ((id: Int) -> Unit) = {}
){
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
        .fillMaxWidth()
        .padding(top = 1.dp)
        .clickable { onClick(pokemon.id) }
        .semantics(mergeDescendants = true){
        }.clearAndSetSemantics {
            contentDescription = pokemon.species
        },
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(colorId))
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
        ){
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .padding(8.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(pokemon.picture)
                        .crossfade(true)
                        .build(),
                    stringResource(id = R.string.pokemon_image, pokemon.species),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize(),
                    placeholder = painterResource(id = R.drawable.pokeball),
                    error = painterResource(id = R.drawable.pokeball)
                )
            }
            Row(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(2f)
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(pokemon.species, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextColor)
                    val typing = if (pokemon.secondaryType != null)
                        "${pokemon.primaryType}/${pokemon.secondaryType}"
                    else
                        pokemon.primaryType
                    Text(typing, fontSize = 12.sp, color = TextColor)
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(end = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        String.format("#%04d", pokemon.dexNumbers[region]),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = TextColor
                    )
                }
            }
        }
    }
}