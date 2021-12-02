package com.egorshustov.food2forkkmm.di

import com.egorshustov.food2forkkmm.datasource.cache.DriverFactory
import com.egorshustov.food2forkkmm.datasource.cache.RecipeCache
import com.egorshustov.food2forkkmm.datasource.cache.RecipeCacheImpl
import com.egorshustov.food2forkkmm.datasource.cache.RecipeDatabase
import com.egorshustov.food2forkkmm.datasource.cache.RecipeDatabaseFactory
import com.egorshustov.food2forkkmm.domain.util.DateTimeUtil

class CacheModule {

    private val driverFactory: DriverFactory by lazy { DriverFactory() }

    private val recipeDatabase: RecipeDatabase by lazy { RecipeDatabaseFactory(driverFactory).createDatabase() }

    val recipeCache: RecipeCache by lazy {
        RecipeCacheImpl(
            recipeDatabase = recipeDatabase,
            dateTimeUtil = DateTimeUtil
        )
    }
}