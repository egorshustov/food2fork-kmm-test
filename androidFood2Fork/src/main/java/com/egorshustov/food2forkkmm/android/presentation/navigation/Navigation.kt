package com.egorshustov.food2forkkmm.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.egorshustov.food2forkkmm.android.presentation.recipe_detail.RecipeDetailScreen
import com.egorshustov.food2forkkmm.android.presentation.recipe_detail.RecipeDetailViewModel
import com.egorshustov.food2forkkmm.android.presentation.recipe_list.RecipeListScreen
import com.egorshustov.food2forkkmm.android.presentation.recipe_list.RecipeListViewModel
import com.egorshustov.food2forkkmm.android.util.RECIPE_ID_ARG

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.RecipeList.route) {
        composable(
            route = Screen.RecipeList.route
        ) { navBackStackEntry ->
            val viewModel: RecipeListViewModel = viewModel()
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
            val viewModel: RecipeDetailViewModel = viewModel()
            RecipeDetailScreen(recipeId = viewModel.recipeId.value)
        }
    }
}