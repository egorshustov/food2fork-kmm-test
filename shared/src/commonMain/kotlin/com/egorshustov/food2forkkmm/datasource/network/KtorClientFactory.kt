package com.egorshustov.food2forkkmm.datasource.network

import io.ktor.client.HttpClient

expect class KtorClientFactory {

    fun build(): HttpClient
}