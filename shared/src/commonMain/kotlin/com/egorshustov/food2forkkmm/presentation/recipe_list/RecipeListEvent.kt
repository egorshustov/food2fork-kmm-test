package com.egorshustov.food2forkkmm.presentation.recipe_list

sealed interface RecipeListEvent {

    object LoadRecipes : RecipeListEvent

    object NextPage : RecipeListEvent
}