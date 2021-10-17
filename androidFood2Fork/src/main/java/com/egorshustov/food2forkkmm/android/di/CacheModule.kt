package com.egorshustov.food2forkkmm.android.di

import android.content.Context
import com.egorshustov.food2forkkmm.datasource.cache.DriverFactory
import com.egorshustov.food2forkkmm.datasource.cache.RecipeCache
import com.egorshustov.food2forkkmm.datasource.cache.RecipeCacheImpl
import com.egorshustov.food2forkkmm.datasource.cache.RecipeDatabase
import com.egorshustov.food2forkkmm.datasource.cache.RecipeDatabaseFactory
import com.egorshustov.food2forkkmm.domain.util.DateTimeUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDriverFactory(@ApplicationContext context: Context): DriverFactory = DriverFactory(context)

    @Singleton
    @Provides
    fun provideRecipeDatabase(driverFactory: DriverFactory): RecipeDatabase =
        RecipeDatabaseFactory(driverFactory).createDatabase()

    @Singleton
    @Provides
    fun provideRecipeCache(recipeDatabase: RecipeDatabase): RecipeCache = RecipeCacheImpl(recipeDatabase, DateTimeUtil)
}