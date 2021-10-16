package com.egorshustov.food2forkkmm.datasource.cache

class RecipeDatabaseFactory(private val driverFactory: DriverFactory) {

    fun createDatabase(): RecipeDatabase = RecipeDatabase(driverFactory.createDriver())
}