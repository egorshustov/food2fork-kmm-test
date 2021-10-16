package com.egorshustov.food2forkkmm.datasource.cache

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DriverFactory {

    actual fun createDriver(): SqlDriver = NativeSqliteDriver(RecipeDatabase.Schema, "recipes.db")
}