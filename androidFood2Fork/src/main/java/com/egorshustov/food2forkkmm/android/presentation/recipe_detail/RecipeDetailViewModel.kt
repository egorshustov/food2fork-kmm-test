package com.egorshustov.food2forkkmm.android.presentation.recipe_detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.egorshustov.food2forkkmm.android.di.Dummy
import com.egorshustov.food2forkkmm.android.util.RECIPE_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    dummy: Dummy
) : ViewModel() {

    val recipeId: MutableState<Int?> = mutableStateOf(null)

    init {
        savedStateHandle.get<Int>(RECIPE_ID_ARG)?.let { recipeId ->
            this.recipeId.value = recipeId
        }
        println("RecipeDetailViewModel: ${dummy.description()}")
    }
}