package com.egorshustov.food2forkkmm.usecases

import com.egorshustov.food2forkkmm.datasource.network.RecipeService
import com.egorshustov.food2forkkmm.domain.model.Recipe
import com.egorshustov.food2forkkmm.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRecipeUseCase(private val recipeService: RecipeService) {

    operator fun invoke(id: Int): Flow<Result<Recipe>> = flow {
        emit(Result.Loading)

        try {
            val recipe = recipeService.get(id)
            emit(Result.Success(recipe))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}