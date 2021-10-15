package com.egorshustov.food2forkkmm.usecases

import com.egorshustov.food2forkkmm.datasource.network.RecipeService
import com.egorshustov.food2forkkmm.domain.model.Recipe
import com.egorshustov.food2forkkmm.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRecipesUseCase(private val recipeService: RecipeService) {

    operator fun invoke(page: Int, query: String): Flow<Result<List<Recipe>>> = flow {
        emit(Result.Loading)

        try {
            val recipes = recipeService.search(page, query)
            emit(Result.Success(recipes))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}