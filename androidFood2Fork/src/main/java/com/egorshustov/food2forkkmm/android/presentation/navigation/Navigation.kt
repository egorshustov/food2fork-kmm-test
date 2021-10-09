package com.egorshustov.food2forkkmm.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.egorshustov.food2forkkmm.android.presentation.recipe_detail.RecipeDetailScreen
import com.egorshustov.food2forkkmm.android.presentation.recipe_list.RecipeListScreen

private const val RECIPE_ID_ARG = "recipeId"

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.RecipeList.route) {
        composable(
            route = Screen.RecipeList.route
        ) { navBackStackEntry ->
            RecipeListScreen { recipeId ->
                navController.navigate(Screen.RecipeDetail.route + "/$recipeId")
            }
        }
        composable(
            route = Screen.RecipeDetail.route + "/{$RECIPE_ID_ARG}",
            arguments = listOf(navArgument(RECIPE_ID_ARG) {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            RecipeDetailScreen(recipeId = navBackStackEntry.arguments?.getInt(RECIPE_ID_ARG))
        }
    }
}