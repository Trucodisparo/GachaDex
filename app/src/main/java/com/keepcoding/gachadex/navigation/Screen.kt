package com.keepcoding.gachadex.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument>
){
    object MainMenuScreen: Screen(
        route = "mainmenu",
        arguments = emptyList()
    )

    object PokedexScreen: Screen(
        route = "pokedex",
        arguments = emptyList()
    )

    object DetailsScreen: Screen(
        route = "pokemon",
        arguments = listOf(
            navArgument("id"){
                type = NavType.IntType
                nullable = false
            }
        )
    )

    object EncounterScreen: Screen(
        route = "encounter",
        arguments = listOf(
        navArgument("json"){
            type = NavType.StringType
            nullable = false
            },
        navArgument("registered"){
            type = NavType.BoolType
            nullable = false
            }
        )
    )
}
