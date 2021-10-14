package com.egorshustov.food2forkkmm.android.di

import com.egorshustov.food2forkkmm.datasource.network.KtorClientFactory
import com.egorshustov.food2forkkmm.datasource.network.RecipeService
import com.egorshustov.food2forkkmm.datasource.network.RecipeServiceImpl
import com.egorshustov.food2forkkmm.datasource.network.RecipeServiceImpl.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient = KtorClientFactory().build()

    @Singleton
    @Provides
    fun provideRecipeService(httpClient: HttpClient): RecipeService = RecipeServiceImpl(httpClient, BASE_URL)
}