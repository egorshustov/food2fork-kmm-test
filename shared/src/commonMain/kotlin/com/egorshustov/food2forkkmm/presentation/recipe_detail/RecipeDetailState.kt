package com.egorshustov.food2forkkmm.presentation.recipe_detail

import com.egorshustov.food2forkkmm.domain.model.Recipe

data class RecipeDetailState(
    val isLoading: Boolean = false,
    val recipe: Recipe? = null
)
