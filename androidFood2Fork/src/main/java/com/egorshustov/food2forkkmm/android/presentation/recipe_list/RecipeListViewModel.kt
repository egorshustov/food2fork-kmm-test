package com.egorshustov.food2forkkmm.android.presentation.recipe_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorshustov.food2forkkmm.domain.util.Result
import com.egorshustov.food2forkkmm.presentation.recipe_list.RecipeListState
import com.egorshustov.food2forkkmm.usecases.recipe_list.SearchRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle, // don't need for this VM
    private val searchRecipesUseCase: SearchRecipesUseCase
) : ViewModel() {

    val state: MutableState<RecipeListState> = mutableStateOf(RecipeListState())

    init {
        loadRecipes()
    }

    private fun loadRecipes() = with(state.value) {
        searchRecipesUseCase(
            page = page,
            query = query
        ).onEach { result ->
            state.value = when (result) {
                is Result.Success -> copy(recipes = recipes + result.data, isLoading = false)
                is Result.Error -> copy(isLoading = false)
                Result.Loading -> copy(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }
}