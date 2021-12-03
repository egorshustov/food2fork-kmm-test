package com.egorshustov.food2forkkmm.presentation.recipe_detail

sealed class RecipeDetailEvent { // KMM does not support Kotlin Sealed interfaces

    data class GetRecipe(val recipeId: Int) : RecipeDetailEvent()

    object OnRemoveHeadMessageFromQueue : RecipeDetailEvent()
}