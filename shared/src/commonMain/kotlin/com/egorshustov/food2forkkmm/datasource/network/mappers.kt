package com.egorshustov.food2forkkmm.datasource.network

import com.egorshustov.food2forkkmm.datasource.network.model.RecipeDto
import com.egorshustov.food2forkkmm.domian.model.Recipe
import com.egorshustov.food2forkkmm.domian.util.DateTimeUtil

fun RecipeDto.toRecipe(): Recipe = Recipe(
    id = pk,
    title = title,
    featuredImage = featuredImage,
    rating = rating,
    publisher = publisher,
    sourceUrl = sourceUrl,
    ingredients = ingredients,
    dateAdded = DateTimeUtil.toLocalDate(longDateAdded.toDouble()),
    dateUpdated = DateTimeUtil.toLocalDate(longDateUpdated.toDouble())
)

fun List<RecipeDto>.toRecipeList(): List<Recipe> = map { it.toRecipe() }