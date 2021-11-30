package com.egorshustov.food2forkkmm.presentation.recipe_detail

sealed interface RecipeDetailEvent {

    data class GetRecipe(val recipeId: Int) : RecipeDetailEvent

    object OnRemoveHeadMessageFromQueue : RecipeDetailEvent
}