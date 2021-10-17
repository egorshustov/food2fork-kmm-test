package com.egorshustov.food2forkkmm.usecases

import com.egorshustov.food2forkkmm.datasource.cache.RecipeCache
import com.egorshustov.food2forkkmm.datasource.network.RecipeService
import com.egorshustov.food2forkkmm.domain.model.Recipe
import com.egorshustov.food2forkkmm.domain.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetRecipeUseCase(
    private val recipeService: RecipeService,
    private val recipeCache: RecipeCache
) {

    operator fun invoke(id: Int): Flow<Result<Recipe>> = flow {
        emit(Result.Loading)

        try {
            val cachedRecipe = recipeCache.get(id)
            if (cachedRecipe == null) {
                val fetchedRecipe = recipeService.get(id)
                emit(Result.Success(fetchedRecipe))
                recipeCache.insert(fetchedRecipe)
            } else {
                emit(Result.Success(cachedRecipe))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }.flowOn(Dispatchers.Default)
}