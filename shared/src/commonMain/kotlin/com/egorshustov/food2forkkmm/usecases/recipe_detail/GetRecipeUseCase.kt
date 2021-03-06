package com.egorshustov.food2forkkmm.usecases.recipe_detail

import com.egorshustov.food2forkkmm.datasource.cache.RecipeCache
import com.egorshustov.food2forkkmm.datasource.network.RecipeService
import com.egorshustov.food2forkkmm.dispatchers.ioDispatcher
import com.egorshustov.food2forkkmm.domain.model.Recipe
import com.egorshustov.food2forkkmm.domain.util.CommonFlow
import com.egorshustov.food2forkkmm.domain.util.Result
import com.egorshustov.food2forkkmm.domain.util.asCommonFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetRecipeUseCase(
    private val recipeService: RecipeService,
    private val recipeCache: RecipeCache
) {

    operator fun invoke(id: Int): CommonFlow<Result> = flow {
        emit(Result.Loading)

        try {
            if (id == 1 || id == 5) throw Exception("Invalid RecipeId: $id") // this is for errors handling testing
            val cachedRecipe: Recipe? = recipeCache.get(id)
            if (cachedRecipe == null) {
                val fetchedRecipe: Recipe = recipeService.get(id)
                emit(Result.Success(fetchedRecipe))
                recipeCache.insert(fetchedRecipe)
            } else {
                emit(Result.Success(cachedRecipe))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }.flowOn(ioDispatcher).asCommonFlow()
}