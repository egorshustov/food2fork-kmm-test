package com.egorshustov.food2forkkmm.datasource.cache

import com.egorshustov.food2forkkmm.domain.model.Recipe
import com.egorshustov.food2forkkmm.domain.util.DateTimeUtil

fun Recipe_Entity.toRecipe(): Recipe = Recipe(
    id = id.toInt(),
    title = title,
    publisher = publisher,
    featuredImage = featured_image,
    rating = rating.toInt(),
    sourceUrl = source_url,
    ingredients = ingredients.toStringList(),
    dateAdded = DateTimeUtil.toLocalDate(date_added),
    dateUpdated = DateTimeUtil.toLocalDate(date_updated)
)

fun List<Recipe_Entity>.toRecipeList(): List<Recipe> = map { it.toRecipe() }

fun List<String>.toCommonString(): String {
    val stringBuilder = StringBuilder()
    forEach { stringBuilder.append("$it,") }
    return stringBuilder.toString()
}

fun String.toStringList(): List<String> = split(",")
