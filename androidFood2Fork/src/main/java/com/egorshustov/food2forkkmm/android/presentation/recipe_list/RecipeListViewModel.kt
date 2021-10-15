package com.egorshustov.food2forkkmm.android.presentation.recipe_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorshustov.food2forkkmm.usecases.SearchRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle, // don't need for this VM
    private val searchRecipesUseCase: SearchRecipesUseCase
) : ViewModel() {

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        searchRecipesUseCase(page = 1, query = "chicken").onEach { result ->
            println("RecipeListVM: $result")
        }.launchIn(viewModelScope)
    }
}