package com.egorshustov.food2forkkmm.datasource.cache

import com.egorshustov.food2forkkmm.datasource.network.RecipeServiceImpl.Companion.RECIPE_PAGINATION_PAGE_SIZE
import com.egorshustov.food2forkkmm.domain.model.Recipe
import com.egorshustov.food2forkkmm.domain.util.DateTimeUtil

class RecipeCacheImpl(
    recipeDatabase: RecipeDatabase,
    private val dateTimeUtil: DateTimeUtil
) : RecipeCache {

    private val queries: RecipeDbQueries = recipeDatabase.recipeDbQueries

    override fun insert(recipe: Recipe) {
        queries.insertRecipe(
            id = recipe.id.toLong(),
            title = recipe.title,
            publisher = recipe.publisher,
            featured_image = recipe.featuredImage,
            rating = recipe.rating.toLong(),
            source_url = recipe.sourceUrl,
            ingredients = recipe.ingredients.toCommonString(),
            date_added = dateTimeUtil.toEpochMilliseconds(recipe.dateAdded),
            date_updated = dateTimeUtil.toEpochMilliseconds(recipe.dateUpdated)
        )
    }

    override fun insert(recipes: List<Recipe>) {
        recipes.forEach { insert(it) }
    }

    override fun search(query: String, page: Int): List<Recipe> = queries.searchRecipes(
        query = query,
        pageSize = RECIPE_PAGINATION_PAGE_SIZE.toLong(),
        offset = ((page - 1) * RECIPE_PAGINATION_PAGE_SIZE).toLong()
    ).executeAsList().toRecipeList()

    override fun getAll(page: Int): List<Recipe> = queries.getAllRecipes(
        pageSize = RECIPE_PAGINATION_PAGE_SIZE.toLong(),
        offset = ((page - 1) * RECIPE_PAGINATION_PAGE_SIZE).toLong()
    ).executeAsList().toRecipeList()

    override fun get(recipeId: Int): Recipe? = try {
        queries.getRecipeById(id = recipeId.toLong()).executeAsOne().toRecipe()
    } catch (e: NullPointerException) {
        null
    }
}