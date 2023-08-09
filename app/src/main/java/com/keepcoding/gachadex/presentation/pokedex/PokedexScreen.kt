package com.keepcoding.gachadex.presentation.pokedex

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Colors
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.DropdownMenu
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.keepcoding.gachadex.common.DexConfig
import com.keepcoding.gachadex.domain.model.PokedexEntryModel
import com.keepcoding.gachadex.ui.theme.TextColor
import org.koin.androidx.compose.koinViewModel
import org.koin.core.component.getScopeId

@Composable
fun PokedexScreen(
    vm: PokedexViewModel = koinViewModel(),
    onItemClick: (id: Int) -> Unit
    ) {

    val state = vm.pokedex.collectAsStateWithLifecycle()
    var expanded = remember{mutableStateOf(false)}

    val regions = vm.getAvailableRegions()
    var selectedRegion = regions.indexOf(state.value.currentRegion.replaceFirstChar { it.uppercase() })
    vm.getData()

    if(state.value.isLoaded && !state.value.isError){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("Pokédex", fontSize = 24.sp, modifier = Modifier.padding(top=8.dp), color = TextColor)
            Text(state.value.dexCompletion, fontSize = 18.sp, modifier = Modifier.padding(bottom=8.dp), color = TextColor )
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    regions[selectedRegion],
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth().clickable { expanded.value = true }.padding(8.dp),
                    textAlign = TextAlign.Center,
                    color = TextColor
                )
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    regions.forEachIndexed { index, regionName ->
                        DropdownMenuItem(onClick = {
                            selectedRegion = index
                            expanded.value = false
                            vm.getData(regions[selectedRegion].lowercase())
                        }) {
                            Text(regionName, modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center)
                        }
                    }
                }
            }
            LazyColumn(){
                items(state.value.list.size){
                    val pokemon = state.value.list[it]
                    PokedexItem(pokemon, onItemClick)
                }
            }
        }
    }
}

@Preview
@Composable
fun PokedexScreenPreview(){
    PokedexScreen(){}
}