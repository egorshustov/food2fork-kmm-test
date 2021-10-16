package com.egorshustov.food2forkkmm.datasource.cache

import com.squareup.sqldelight.db.SqlDriver

expect class DriverFactory {

    fun createDriver(): SqlDriver
}