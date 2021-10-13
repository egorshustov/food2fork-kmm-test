package com.egorshustov.food2forkkmm.datasource.network

import com.egorshustov.food2forkkmm.datasource.network.model.RecipeDto
import com.egorshustov.food2forkkmm.domain.model.Recipe
import com.egorshustov.food2forkkmm.domain.util.DateTimeUtil

const val NO_VALUE = -1

fun RecipeDto.toRecipe(): Recipe = Recipe(
    id = pk ?: NO_VALUE,
    title = title.orEmpty(),
    featuredImage = featuredImage.orEmpty(),
    rating = rating ?: NO_VALUE,
    publisher = publisher.orEmpty(),
    sourceUrl = sourceUrl.orEmpty(),
    ingredients = ingredients ?: emptyList(),
    dateAdded = DateTimeUtil.toLocalDate(longDateAdded?.toDouble() ?: NO_VALUE.toDouble()),
    dateUpdated = DateTimeUtil.toLocalDate(longDateUpdated?.toDouble() ?: NO_VALUE.toDouble())
)

fun List<RecipeDto>.toRecipeList(): List<Recipe> = map { it.toRecipe() }