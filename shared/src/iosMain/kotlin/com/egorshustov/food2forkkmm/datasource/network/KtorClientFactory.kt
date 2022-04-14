package com.egorshustov.food2forkkmm.datasource.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual class KtorClientFactory {

    actual fun build(): HttpClient = HttpClient(Darwin) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // if the server sends extra fields, ignore
            })
        }
        install(HttpTimeout) {
            connectTimeoutMillis = 3000
        }
    }

}