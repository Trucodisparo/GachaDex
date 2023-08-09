package com.keepcoding.gachadex.presentation.mainmenu

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextClock
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.keepcoding.gachadex.R
import com.keepcoding.gachadex.domain.model.PokedexEntryModel
import com.keepcoding.gachadex.navigation.NavigationGraph
import com.keepcoding.gachadex.presentation.pokedex.PokedexViewModel
import com.keepcoding.gachadex.ui.theme.GachaDexTheme
import org.koin.androidx.compose.koinViewModel
import java.util.concurrent.TimeUnit
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GachaDexTheme {
                Surface(color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxSize()) {
                    NavigationGraph()
                }
            }
        }
    }
}

@Composable
fun MainMenuScreen(
    vm: MainMenuViewModel = koinViewModel(),
    pokedexVM: PokedexViewModel = koinViewModel(),
    onPokedexClick: () -> Unit,
    onEncounterClick: () -> Unit,
) {
    val countDown = vm.countDown.collectAsStateWithLifecycle()
    val deleteConfirm = remember{mutableStateOf(false)}
    val context = LocalContext.current
    val secretClickCount = remember{mutableStateOf(0)}

    LaunchedEffect(vm){
        //start only if there's no running timer
        if(countDown.value == "Encounter")
            vm.startTimer()
    }
    Row(modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {}
        Column(
            modifier = Modifier.weight(2f),
        ) {
            Image(painter = painterResource(id = R.mipmap.gachadex),
                contentDescription = "GachaDex Logo",
                modifier = Modifier.fillMaxWidth().clickable{
                    secretClickCount.value += 1
                    if(secretClickCount.value == 3){
                        secretClickCount.value = 0
                        pokedexVM.unlockNextDex()
                        Toast.makeText(context, "Next Dex Unlocked", Toast.LENGTH_SHORT).show()
                    }
                })
            Spacer(modifier = Modifier.size(20.dp))
            MenuButton("Pokédex", onPokedexClick)
            MenuButton(countDown.value, enabled = countDown.value == "Encounter", onClick = {
                vm.setLastEncounter()
                onEncounterClick()
            })
            if(!deleteConfirm.value)
                MenuButton("Reset Pokédex", {deleteConfirm.value = true})
            else
                MenuButton("Press to confirm", {
                    pokedexVM.resetDex()
                    deleteConfirm.value = false
                    Toast.makeText(context, "Pokédex was reset", Toast.LENGTH_SHORT).show()
                })
        }
        Column(modifier = Modifier.weight(1f)) {}
    }
}

@Composable
fun MenuButton(text: String, onClick: () -> Unit, enabled: Boolean = true){
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(2.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
        enabled = enabled
    ) {
        Text(text)
    }
}