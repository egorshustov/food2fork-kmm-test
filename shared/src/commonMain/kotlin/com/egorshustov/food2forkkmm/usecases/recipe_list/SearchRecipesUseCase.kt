package com.egorshustov.food2forkkmm.usecases.recipe_list

import com.egorshustov.food2forkkmm.datasource.cache.RecipeCache
import com.egorshustov.food2forkkmm.datasource.network.RecipeService
import com.egorshustov.food2forkkmm.domain.model.Recipe
import com.egorshustov.food2forkkmm.domain.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchRecipesUseCase(
    private val recipeService: RecipeService,
    private val recipeCache: RecipeCache
) {

    operator fun invoke(page: Int, query: String = ""): Flow<Result<List<Recipe>>> = flow {
        emit(Result.Loading)

        try {
            if (query == "error") throw Exception("Forcing an error... Search FAILED")
            val fetchedRecipes = recipeService.search(page, query)
            emit(Result.Success(fetchedRecipes))
            recipeCache.insert(fetchedRecipes)
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }.flowOn(Dispatchers.Default)
}