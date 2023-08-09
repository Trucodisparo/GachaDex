package com.keepcoding.gachadex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationGraph(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MainMenuScreen.route
    ){
        addMainMenu(navController)
        addPokedex(navController)
        addEncounter(navController)
        addPokedexDetails(navController)
    }
}