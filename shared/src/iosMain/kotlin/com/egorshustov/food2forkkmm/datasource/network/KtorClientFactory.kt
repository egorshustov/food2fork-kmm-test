package com.egorshustov.food2forkkmm.datasource.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.Ios
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json

actual class KtorClientFactory {

    actual fun build(): HttpClient = HttpClient(Ios) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                Json {
                    ignoreUnknownKeys = true // if the server sends extra fields, ignore
                }
            )
        }
        install(HttpTimeout) {
            connectTimeoutMillis = 3000
        }
    }

}