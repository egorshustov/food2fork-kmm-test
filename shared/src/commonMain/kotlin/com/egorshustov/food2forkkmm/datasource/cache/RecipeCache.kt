package com.egorshustov.food2forkkmm.datasource.cache

import com.egorshustov.food2forkkmm.domain.model.Recipe

interface RecipeCache {

    fun insert(recipe: Recipe)

    fun insert(recipes: List<Recipe>)

    fun search(query: String, page: Int): List<Recipe>

    fun getAll(page: Int): List<Recipe>

    @Throws(NullPointerException::class)
    fun get(recipeId: Int): Recipe?
}