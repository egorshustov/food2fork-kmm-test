package com.egorshustov.food2forkkmm.android.di

import com.egorshustov.food2forkkmm.datasource.cache.RecipeCache
import com.egorshustov.food2forkkmm.datasource.network.RecipeService
import com.egorshustov.food2forkkmm.usecases.recipe_detail.GetRecipeUseCase
import com.egorshustov.food2forkkmm.usecases.recipe_list.SearchRecipesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Singleton
    @Provides
    fun provideSearchRecipesUseCase(
        recipeService: RecipeService,
        recipeCache: RecipeCache
    ): SearchRecipesUseCase = SearchRecipesUseCase(recipeService, recipeCache)

    @Singleton
    @Provides
    fun provideGetRecipeUseCase(
        recipeService: RecipeService,
        recipeCache: RecipeCache
    ): GetRecipeUseCase = GetRecipeUseCase(recipeService, recipeCache)
}