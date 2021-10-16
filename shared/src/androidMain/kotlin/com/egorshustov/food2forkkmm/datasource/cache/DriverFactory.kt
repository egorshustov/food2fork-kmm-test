package com.egorshustov.food2forkkmm.datasource.cache

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DriverFactory(private val context: Context) {

    actual fun createDriver(): SqlDriver = AndroidSqliteDriver(RecipeDatabase.Schema, context, "recipes.db")
}