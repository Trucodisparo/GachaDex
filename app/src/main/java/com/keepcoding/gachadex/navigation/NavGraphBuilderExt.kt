package com.keepcoding.gachadex.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.keepcoding.gachadex.presentation.mainmenu.MainMenuScreen
import com.keepcoding.gachadex.presentation.encounter.EncounterScreen
import com.keepcoding.gachadex.presentation.pokedex.PokedexScreen
import com.keepcoding.gachadex.presentation.pokedex.details.DetailsScreen

fun NavGraphBuilder.addMainMenu(navController: NavController){
    composable(Screen.MainMenuScreen.route){
        MainMenuScreen (
            onPokedexClick = {
                navController.navigate(Screen.PokedexScreen.route)
            },
            onEncounterClick = {
                navController.navigate(Screen.EncounterScreen.route)
            }
        )
    }
}

fun NavGraphBuilder.addPokedex(navController: NavController){
    composable(Screen.PokedexScreen.route){
        PokedexScreen(onItemClick = { id: Int ->
            navController.navigate("${Screen.DetailsScreen.route}/$id")
        })
    }
}

fun NavGraphBuilder.addEncounter(navController: NavController){
    composable(route = Screen.EncounterScreen.route){
        EncounterScreen { navController.popBackStack()}
    }
}

fun NavGraphBuilder.addPokedexDetails(navController: NavController){
    composable(route = Screen.DetailsScreen.route + "/{id}",
        arguments = Screen.DetailsScreen.arguments){ navBackStackEntry ->
        val id = navBackStackEntry.arguments?.getInt("id") ?: 0
        DetailsScreen(id)
    }

}