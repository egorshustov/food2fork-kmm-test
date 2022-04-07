package com.egorshustov.food2forkkmm.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
        ) {
            val viewModel = hiltViewModel<RecipeListViewModel>()
            RecipeListScreen(
                state = viewModel.state.value,
                onTriggerEvent = viewModel::onTriggerEvent,
                onClickRecipeListItem = { recipeId ->
                    navController.navigate(Screen.RecipeDetail.route + "/$recipeId")
                }
            )
        }
        composable(
            route = Screen.RecipeDetail.route + "/{$RECIPE_ID_ARG}",
            arguments = listOf(navArgument(RECIPE_ID_ARG) {
                type = NavType.IntType
            })
        ) {
            val viewModel = hiltViewModel<RecipeDetailViewModel>()
            RecipeDetailScreen(
                state = viewModel.state.value,
                onTriggerEvent = viewModel::onTriggerEvent
            )
        }
    }
}