package com.egorshustov.food2forkkmm.presentation.recipe_list

sealed interface RecipeListEvent {

    object LoadRecipes : RecipeListEvent

    object NextPage : RecipeListEvent

    object NewSearch : RecipeListEvent

    data class OnUpdateQuery(val query: String) : RecipeListEvent
}