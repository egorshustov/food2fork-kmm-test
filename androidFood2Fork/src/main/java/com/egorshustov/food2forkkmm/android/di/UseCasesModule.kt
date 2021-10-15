package com.egorshustov.food2forkkmm.android.di

import com.egorshustov.food2forkkmm.datasource.network.RecipeService
import com.egorshustov.food2forkkmm.usecases.GetRecipeUseCase
import com.egorshustov.food2forkkmm.usecases.SearchRecipesUseCase
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
    fun provideSearchRecipesUseCase(recipeService: RecipeService): SearchRecipesUseCase =
        SearchRecipesUseCase(recipeService)

    @Singleton
    @Provides
    fun provideGetRecipeUseCase(recipeService: RecipeService): GetRecipeUseCase =
        GetRecipeUseCase(recipeService)
}