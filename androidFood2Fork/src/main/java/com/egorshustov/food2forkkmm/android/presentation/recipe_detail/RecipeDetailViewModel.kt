package com.egorshustov.food2forkkmm.android.presentation.recipe_detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorshustov.food2forkkmm.android.util.RECIPE_ID_ARG
import com.egorshustov.food2forkkmm.domain.model.Recipe
import com.egorshustov.food2forkkmm.domain.util.Result
import com.egorshustov.food2forkkmm.usecases.recipe_detail.GetRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRecipeUseCase: GetRecipeUseCase
) : ViewModel() {

    val recipe: MutableState<Recipe?> = mutableStateOf(null)

    init {
        savedStateHandle.get<Int>(RECIPE_ID_ARG)?.let { recipeId ->
            getRecipe(recipeId)
        }
    }

    private fun getRecipe(id: Int) {
        getRecipeUseCase(id).onEach { result ->
            println("RecipeDetailVM: $result")
            if (result is Result.Success) {
                recipe.value = result.data
            }
        }.launchIn(viewModelScope)
    }
}