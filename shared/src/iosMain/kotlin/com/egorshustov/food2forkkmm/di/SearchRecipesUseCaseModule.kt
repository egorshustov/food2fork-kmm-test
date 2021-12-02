package com.egorshustov.food2forkkmm.di

import com.egorshustov.food2forkkmm.usecases.recipe_list.SearchRecipesUseCase

class SearchRecipesUseCaseModule(
    private val networkModule: NetworkModule,
    private val cacheModule: CacheModule
) {

    val searchRecipesUseCase: SearchRecipesUseCase by lazy {
        SearchRecipesUseCase(
            recipeService = networkModule.recipeService,
            recipeCache = cacheModule.recipeCache
        )
    }
}