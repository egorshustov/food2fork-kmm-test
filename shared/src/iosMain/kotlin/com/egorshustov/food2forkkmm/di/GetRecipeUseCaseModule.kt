package com.egorshustov.food2forkkmm.di

import com.egorshustov.food2forkkmm.usecases.recipe_detail.GetRecipeUseCase

class GetRecipeUseCaseModule(
    private val networkModule: NetworkModule,
    private val cacheModule: CacheModule
) {

    val getRecipeUseCase: GetRecipeUseCase by lazy {
        GetRecipeUseCase(
            recipeService = networkModule.recipeService,
            recipeCache = cacheModule.recipeCache
        )
    }
}