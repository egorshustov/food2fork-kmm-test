package com.egorshustov.food2forkkmm.presentation.recipe_detail

import com.egorshustov.food2forkkmm.domain.model.Recipe
import com.egorshustov.food2forkkmm.presentation.model.GenericMessageInfo
import com.egorshustov.food2forkkmm.presentation.model.Queue

data class RecipeDetailState(
    val isLoading: Boolean = false,
    val recipe: Recipe? = null,
    val queue: Queue<GenericMessageInfo> = Queue(mutableListOf())
) {

    constructor() : this(
        isLoading = false,
        recipe = null,
        queue = Queue(mutableListOf())
    )
}